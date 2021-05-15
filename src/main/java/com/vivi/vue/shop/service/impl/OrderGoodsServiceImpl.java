package com.vivi.vue.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.dao.OrderGoodsDao;
import com.vivi.vue.shop.entity.OrderGoodsEntity;
import com.vivi.vue.shop.service.OrderGoodsService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("orderGoodsService")
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsDao, OrderGoodsEntity> implements OrderGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderGoodsEntity> page = this.page(
                new Query<OrderGoodsEntity>().getPage(params),
                new QueryWrapper<OrderGoodsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<OrderGoodsEntity> listByOrderId(Integer orderId) {

        return this.list(new QueryWrapper<OrderGoodsEntity>().eq("order_id", orderId));
    }

    @Override
    public boolean removeByOrderId(Integer orderId) {
        return this.remove(new QueryWrapper<OrderGoodsEntity>().eq("order_id", orderId));
    }

}