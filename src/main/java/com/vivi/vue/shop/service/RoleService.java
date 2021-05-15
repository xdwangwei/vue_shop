package com.vivi.vue.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.entity.RoleEntity;
import com.vivi.vue.shop.vo.RoleVO;
import com.vivi.vue.shop.vo.RoleWithRightVO;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface RoleService extends IService<RoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取所有角色及其权限
     * @return
     */
    List<RoleWithRightVO> listWithRights();

    /**
     * 添加新角色
     * @return
     */
    RoleVO add(RoleVO roleVO);

    /**
     * 查询单个角色
     * @param roleId
     * @return
     */
    RoleVO getOne(Integer roleId);

    /**
     * 为角色赋予权限
     * @param roleId
     * @param rights 以 `,` 分割的权限 ID 列表
     */
    void assignRights(Integer roleId, JSONObject rights);

    /**
     * 移除指定角色指定权限，并返回该角色最新权限列表
     * @param roleId
     * @param rightId
     * @return
     */
    RoleWithRightVO removeRight(Integer roleId, Integer rightId);

    /**
     * 修改用户信息
     * @param roleId
     * @param roleVO
     * @return
     */
    RoleVO update(Integer roleId, RoleVO roleVO);
}

