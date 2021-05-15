package com.vivi.vue.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.dao.PermissionDao;
import com.vivi.vue.shop.entity.PermissionApiEntity;
import com.vivi.vue.shop.entity.PermissionEntity;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.exception.BizException;
import com.vivi.vue.shop.service.PermissionApiService;
import com.vivi.vue.shop.service.PermissionService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;
import com.vivi.vue.shop.vo.MenuTreeVO;
import com.vivi.vue.shop.vo.RightsListVO;
import com.vivi.vue.shop.vo.RightsTreeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, PermissionEntity> implements PermissionService {

    @Autowired
    PermissionApiService permissionApiService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PermissionEntity> page = this.page(
                new Query<PermissionEntity>().getPage(params),
                new QueryWrapper<PermissionEntity>()
        );

        return new PageUtils(page);
    }

    /**
     *
     * @param type  list 返回 List<RightsListVO>
     *              tree 返回 List<RightsTreeVO>
     * @return
     */
    @Override
    public List listByType(String type) {
        if (type.equals("list")) {
            return this.permissionList();
        } else if (type.equals("tree")) {
            return this.permissionTree();
        } else {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "路径变量type只能为list或tree");
        }
    }

    /**
     * 获取前两级菜单
     * @return
     */
    @Override
    public List<MenuTreeVO> level2Permission() {
        /*先得到全部列表*/
        List<MenuTreeVO> menuTreeVOS = this.list().stream().map(permission -> convertPermissionEntity2MenuTreeVO(permission)).collect(Collectors.toList());
        // 获取出一级菜单
        List<MenuTreeVO> collect = menuTreeVOS.stream()
                .filter(menu -> menu.getPid().equals("0"))
                /* 为其赋值二级菜单*/
                .map(menu -> {
                    menu.setChildren(menuTreeVOS.stream().filter(menu2 -> menu2.getPid().equals(menu.getId().toString())).collect(Collectors.toList()));
                    return menu;
                })
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * 有限范围内的权限集合
     * @param psIds
     * @return
     */
    @Override
    public List<RightsTreeVO> limitPermissionTree(Set<Integer> psIds) {
        if (CollectionUtils.isEmpty(psIds)) {
            return new ArrayList<>();
        }
        // 先查出全部可选权限列表
        List<PermissionEntity> list = this.baseMapper.limitList(psIds);
        List<RightsTreeVO> treeVOList = list.stream().map(permission -> convertPermissionEntity2RightsTreeVO(permission)).collect(Collectors.toList());
        // 再组合成树形结构
        // 先过滤出一级菜单
        List<RightsTreeVO> level1s = treeVOList.stream()
                .filter(treeVO -> treeVO.getPid().equals("0"))
                // 再为其赋值子菜单
                .map(level1 -> getChildren(level1, treeVOList))
                .collect(Collectors.toList());
        return level1s;
    }

    /**
     * 以列表形式返回所有权限数据
     * @return
     */
    private List<RightsListVO> permissionList() {
        List<PermissionEntity> list = this.list();
        List<RightsListVO> collect = list.stream().map(permission -> convertPermissionEntity2RightsListVO(permission)).collect(Collectors.toList());
        return collect;
    }

    /**
     * 以树形结构返回所有权限数据
     * 属性结构的第一层就是一级菜单
     * @return
     */
    private List<RightsTreeVO> permissionTree() {
        // 先查出全部权限列表
        List<RightsTreeVO> treeVOList = this.list().stream().map(permission -> convertPermissionEntity2RightsTreeVO(permission)).collect(Collectors.toList());
        // 再组合成树形结构
        // 先过滤出一级菜单
        List<RightsTreeVO> level1s = treeVOList.stream()
                .filter(treeVO -> treeVO.getPid().equals("0"))
                    // 再为其赋值子级菜单
                .map(level1 -> getChildren(level1, treeVOList))
                .collect(Collectors.toList());
        return level1s;
    }

    /**
     * 传入一个一级菜单和全部菜单，递归找到这个菜单的全部孩子，设置到他相应的属性，再把它返回
     * @return
     */
    private RightsTreeVO getChildren(RightsTreeVO treeVO, List<RightsTreeVO> all) {
        treeVO.setChildren(all.stream()
                // 找到他的下一级
                .filter(permission -> StringUtils.equals(permission.getPid(), treeVO.getId().toString()))
                // 每个下一级，通过这个函数继续寻找自己的下一级
                .map(permission -> {
                    /*处理三级菜单的父id路径*/
                    if (permission.getLevel() == 2) {
                        permission.setPid(permission.getPid() + "," + treeVO.getPid());
                    }
                    return getChildren(permission, all);
                })
                .collect(Collectors.toList()));
        return treeVO;
    }


    private RightsListVO convertPermissionEntity2RightsListVO(PermissionEntity permission) {
        RightsListVO rightsListVO = new RightsListVO();
        rightsListVO.setId(permission.getPsId());
        rightsListVO.setAuthName(permission.getPsName());
        rightsListVO.setLevel(permission.getPsLevel());
        rightsListVO.setPid(permission.getPsPid().toString());
        // 设置path
        PermissionApiEntity apiEntity = permissionApiService.getByPermissionId(permission.getPsId());
        if (apiEntity != null) {
            rightsListVO.setPath(apiEntity.getPsApiPath());
        }
        return rightsListVO;
    }

    private RightsTreeVO convertPermissionEntity2RightsTreeVO(PermissionEntity permission) {
        RightsTreeVO treeVO = new RightsTreeVO();
        treeVO.setId(permission.getPsId());
        treeVO.setAuthName(permission.getPsName());
        treeVO.setLevel(permission.getPsLevel());
        treeVO.setPid(permission.getPsPid().toString());
        // 设置path
        PermissionApiEntity apiEntity = permissionApiService.getByPermissionId(permission.getPsId());
        if (apiEntity != null) {
            treeVO.setPath(apiEntity.getPsApiPath());
        }
        return treeVO;
    }

    private MenuTreeVO convertPermissionEntity2MenuTreeVO(PermissionEntity permission) {
        MenuTreeVO menuTreeVO = new MenuTreeVO();
        menuTreeVO.setId(permission.getPsId());
        menuTreeVO.setAuthName(permission.getPsName());
        menuTreeVO.setPid(permission.getPsPid().toString());
        // 设置path
        PermissionApiEntity apiEntity = permissionApiService.getByPermissionId(permission.getPsId());
        if (apiEntity != null) {
            menuTreeVO.setPath(apiEntity.getPsApiPath());
        }
        return menuTreeVO;
    }

}