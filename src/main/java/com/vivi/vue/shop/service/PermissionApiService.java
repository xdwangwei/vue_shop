package com.vivi.vue.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.entity.PermissionApiEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface PermissionApiService extends IService<PermissionApiEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据权限id查询整个api信息
     * @param psId
     * @return
     */
    PermissionApiEntity getByPermissionId(Integer psId);
}

