package com.vivi.vue.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.entity.OrderGoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品订单关联表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface OrderGoodsService extends IService<OrderGoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询指定订单列表
     * @param orderId
     * @return
     */
    List<OrderGoodsEntity> listByOrderId(Integer orderId);

    /**
     * 删除指定订单的列表信息
     * @param orderId
     * @return
     */
    boolean removeByOrderId(Integer orderId);
}

