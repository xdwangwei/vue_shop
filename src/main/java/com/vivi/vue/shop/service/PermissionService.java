package com.vivi.vue.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.entity.PermissionEntity;
import com.vivi.vue.shop.vo.MenuTreeVO;
import com.vivi.vue.shop.vo.RightsTreeVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface PermissionService extends IService<PermissionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取列表形式或树形结构的权限
     * @param type  list 返回 List<RightsListVO>
     *              tree 返回 List<RightsTreeVO>
     * @return
     */
    List listByType(String type);

    /**
     * 所有1、2级菜单
     * @return
     */
    List<MenuTreeVO> level2Permission();

    /**
     * 指定范围的权限集合，树形结构返回
     * @param psIds
     * @return
     */
    List<RightsTreeVO> limitPermissionTree(Set<Integer> psIds);
}

