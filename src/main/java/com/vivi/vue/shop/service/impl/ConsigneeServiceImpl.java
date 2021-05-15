package com.vivi.vue.shop.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;

import com.vivi.vue.shop.dao.ConsigneeDao;
import com.vivi.vue.shop.entity.ConsigneeEntity;
import com.vivi.vue.shop.service.ConsigneeService;


@Service("consigneeService")
public class ConsigneeServiceImpl extends ServiceImpl<ConsigneeDao, ConsigneeEntity> implements ConsigneeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ConsigneeEntity> page = this.page(
                new Query<ConsigneeEntity>().getPage(params),
                new QueryWrapper<ConsigneeEntity>()
        );

        return new PageUtils(page);
    }

}