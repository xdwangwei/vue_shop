package com.vivi.vue.shop.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;

import com.vivi.vue.shop.dao.Report1Dao;
import com.vivi.vue.shop.entity.Report1Entity;
import com.vivi.vue.shop.service.Report1Service;


@Service("report1Service")
public class Report1ServiceImpl extends ServiceImpl<Report1Dao, Report1Entity> implements Report1Service {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Report1Entity> page = this.page(
                new Query<Report1Entity>().getPage(params),
                new QueryWrapper<Report1Entity>()
        );

        return new PageUtils(page);
    }

}