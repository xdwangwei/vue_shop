package com.vivi.vue.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.dao.GoodsPicsDao;
import com.vivi.vue.shop.entity.GoodsPicsEntity;
import com.vivi.vue.shop.service.GoodsPicsService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("goodsPicsService")
public class GoodsPicsServiceImpl extends ServiceImpl<GoodsPicsDao, GoodsPicsEntity> implements GoodsPicsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsPicsEntity> page = this.page(
                new Query<GoodsPicsEntity>().getPage(params),
                new QueryWrapper<GoodsPicsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<GoodsPicsEntity> listByGoodsId(Integer goodsId) {

        return this.list(new QueryWrapper<GoodsPicsEntity>().eq("goods_id", goodsId));
    }

    @Override
    public boolean removeByGoodsId(Integer goodsId) {
        return this.remove(new QueryWrapper<GoodsPicsEntity>().eq("goods_id", goodsId));
    }

}