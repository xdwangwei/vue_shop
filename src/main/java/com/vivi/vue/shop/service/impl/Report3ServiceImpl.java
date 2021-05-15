package com.vivi.vue.shop.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;

import com.vivi.vue.shop.dao.Report3Dao;
import com.vivi.vue.shop.entity.Report3Entity;
import com.vivi.vue.shop.service.Report3Service;


@Service("report3Service")
public class Report3ServiceImpl extends ServiceImpl<Report3Dao, Report3Entity> implements Report3Service {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Report3Entity> page = this.page(
                new Query<Report3Entity>().getPage(params),
                new QueryWrapper<Report3Entity>()
        );

        return new PageUtils(page);
    }

}