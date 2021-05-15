package com.vivi.vue.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.dao.PermissionApiDao;
import com.vivi.vue.shop.entity.PermissionApiEntity;
import com.vivi.vue.shop.service.PermissionApiService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("permissionApiService")
public class PermissionApiServiceImpl extends ServiceImpl<PermissionApiDao, PermissionApiEntity> implements PermissionApiService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PermissionApiEntity> page = this.page(
                new Query<PermissionApiEntity>().getPage(params),
                new QueryWrapper<PermissionApiEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PermissionApiEntity getByPermissionId(Integer psId) {

        return this.getOne(new QueryWrapper<PermissionApiEntity>().eq("ps_id", psId));
    }

}