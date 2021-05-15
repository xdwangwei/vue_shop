package com.vivi.vue.shop.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;

import com.vivi.vue.shop.dao.ExpressDao;
import com.vivi.vue.shop.entity.ExpressEntity;
import com.vivi.vue.shop.service.ExpressService;


@Service("expressService")
public class ExpressServiceImpl extends ServiceImpl<ExpressDao, ExpressEntity> implements ExpressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ExpressEntity> page = this.page(
                new Query<ExpressEntity>().getPage(params),
                new QueryWrapper<ExpressEntity>()
        );

        return new PageUtils(page);
    }

}