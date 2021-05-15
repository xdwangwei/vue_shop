package com.vivi.vue.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.dao.RoleDao;
import com.vivi.vue.shop.entity.RoleEntity;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.exception.BizException;
import com.vivi.vue.shop.service.PermissionService;
import com.vivi.vue.shop.service.RoleService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;
import com.vivi.vue.shop.vo.RoleVO;
import com.vivi.vue.shop.vo.RoleWithRightVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Autowired
    PermissionService permissionService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RoleEntity> page = this.page(
                new Query<RoleEntity>().getPage(params),
                new QueryWrapper<RoleEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<RoleWithRightVO> listWithRights() {

        List<RoleEntity> list = this.list();
        List<RoleWithRightVO> collect = list.stream().map(role -> {
            RoleWithRightVO roleWithRightVO = new RoleWithRightVO();
            BeanUtils.copyProperties(role, roleWithRightVO);
            // 设置权限
            if (!StringUtils.isEmpty(role.getPsIds())) {
                // 分割权限字符串，将这些权限组合成树形结构
                Set<Integer> psIds = Arrays.stream(StringUtils.split(role.getPsIds(), ",")).filter(str -> StringUtils.isNotBlank(str)).map(str -> Integer.parseInt(str)).collect(Collectors.toSet());
                roleWithRightVO.setChildren(permissionService.limitPermissionTree(psIds));
            }
            return roleWithRightVO;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public RoleVO add(RoleVO roleVO) {
        RoleEntity entity = new RoleEntity();
        entity.setRoleName(roleVO.getRoleName());
        if (!StringUtils.isEmpty(roleVO.getRoleDesc())) {
            entity.setRoleDesc(roleVO.getRoleDesc());
        } else {
            entity.setRoleDesc(roleVO.getRoleName());
        }
        this.save(entity);
        return convertRoleEntity2RoleVO(this.getById(entity.getRoleId()));
    }

    @Override
    public RoleVO getOne(Integer roleId) {
        RoleEntity byId = this.getById(roleId);
        if (byId == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "指定角色不存在");
        }
        return convertRoleEntity2RoleVO(byId);
    }

    @Override
    public void assignRights(Integer roleId, JSONObject rights) {
        String rids = (String) rights.get("rids");
        if(StringUtils.isEmpty(rids)) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "请求体中 'rids' 字段非法参数值");
        }
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleId(roleId);
        roleEntity.setPsIds(rids);
        boolean r = this.updateById(roleEntity);
        if (!r) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "插入失败，请检查roleId是否存在或rights是否合法");
        }
    }

    @Override
    public RoleWithRightVO removeRight(Integer roleId, Integer rightId) {
        RoleEntity roleEntity = this.getById(roleId);
        if (roleEntity == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "指定角色不存在");
        }
        // 准备要返回的对象
        RoleWithRightVO roleWithRightVO = new RoleWithRightVO();
        BeanUtils.copyProperties(roleEntity, roleWithRightVO);
        String psIds = roleEntity.getPsIds();
        if (!StringUtils.isEmpty(psIds)) {
            Set<Integer> rightIds = Arrays.stream(psIds.split(",")).filter(str -> StringUtils.isNotBlank(str)).map(str -> Integer.parseInt(str)).collect(Collectors.toSet());
            // 移除指定权限
            rightIds.remove(rightId);
            // 得到新权限值
            String newRightsStr = rightIds.stream().map(id -> id.toString()).collect(Collectors.joining(","));
            // 修改原角色权限值
            RoleEntity entity = new RoleEntity();
            entity.setRoleId(roleEntity.getRoleId());
            entity.setPsIds(newRightsStr);
            this.updateById(entity);
            /*角色新的权限列表*/
            roleWithRightVO.setChildren(permissionService.limitPermissionTree(rightIds));
        }
        return roleWithRightVO;
    }

    @Override
    public RoleVO update(Integer roleId, RoleVO roleVO) {
        RoleEntity roleEntity = this.getById(roleId);
        if (roleEntity == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "指定角色不存在");
        }
        RoleEntity role = new RoleEntity();
        role.setRoleId(roleId);
        role.setRoleName(roleVO.getRoleName());
        role.setRoleDesc(roleVO.getRoleDesc());
        this.updateById(role);
        return convertRoleEntity2RoleVO(this.getById(roleId));
    }

    private RoleVO convertRoleEntity2RoleVO(RoleEntity entity) {
        if (entity == null) {
            return null;
        }
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(entity, roleVO);
        return roleVO;
    }


}