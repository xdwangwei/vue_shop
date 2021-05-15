package com.vivi.vue.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.entity.OrderEntity;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.vo.OrderUpdateVO;
import com.vivi.vue.shop.vo.OrderVO;
import com.vivi.vue.shop.vo.OrdersListPageVO;
import com.vivi.vue.shop.vo.PageQueryVO;

import java.util.Map;

/**
 * 订单表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 分页查询订单列表
     * @param pageQueryVO
     * @return
     */
    OrdersListPageVO queryPage(PageQueryVO pageQueryVO);

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    OrderVO getOne(Integer orderId);

    /**
     * 删除订单及其订单项
     * @param orderId
     */
    void removeCascade(Integer orderId);

    /**
     * 更新商品信息
     * @param orderId
     * @param updateVO
     * @return
     */
    OrderVO updateById(Integer orderId, OrderUpdateVO updateVO);
}

