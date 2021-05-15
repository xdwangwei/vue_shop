package com.vivi.vue.shop.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;

import com.vivi.vue.shop.dao.Report2Dao;
import com.vivi.vue.shop.entity.Report2Entity;
import com.vivi.vue.shop.service.Report2Service;


@Service("report2Service")
public class Report2ServiceImpl extends ServiceImpl<Report2Dao, Report2Entity> implements Report2Service {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Report2Entity> page = this.page(
                new Query<Report2Entity>().getPage(params),
                new QueryWrapper<Report2Entity>()
        );

        return new PageUtils(page);
    }

}