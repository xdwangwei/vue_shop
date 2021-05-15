package com.vivi.vue.shop.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;

import com.vivi.vue.shop.dao.GoodsCatsDao;
import com.vivi.vue.shop.entity.GoodsCatsEntity;
import com.vivi.vue.shop.service.GoodsCatsService;


@Service("goodsCatsService")
public class GoodsCatsServiceImpl extends ServiceImpl<GoodsCatsDao, GoodsCatsEntity> implements GoodsCatsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsCatsEntity> page = this.page(
                new Query<GoodsCatsEntity>().getPage(params),
                new QueryWrapper<GoodsCatsEntity>()
        );

        return new PageUtils(page);
    }

}