package com.vivi.vue.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.dao.OrderDao;
import com.vivi.vue.shop.entity.OrderEntity;
import com.vivi.vue.shop.entity.OrderGoodsEntity;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.exception.BizException;
import com.vivi.vue.shop.service.OrderGoodsService;
import com.vivi.vue.shop.service.OrderService;
import com.vivi.vue.shop.utils.Constant;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;
import com.vivi.vue.shop.vo.OrderUpdateVO;
import com.vivi.vue.shop.vo.OrderVO;
import com.vivi.vue.shop.vo.OrdersListPageVO;
import com.vivi.vue.shop.vo.PageQueryVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    ThreadPoolExecutor executor;

    @Autowired
    OrderGoodsService orderGoodsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrdersListPageVO queryPage(PageQueryVO pageQueryVO) {

        // 构建分页查询参数
        Map<String, Object> params = new HashMap<>();
        params.put(Constant.PAGE, pageQueryVO.getPagenum().toString());
        params.put(Constant.LIMIT, pageQueryVO.getPagesize().toString());
        // 分页查询
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        // 判断请求参数，可以传id或name
        String query = pageQueryVO.getQuery();
        if (!StringUtils.isEmpty(query)) {
            wrapper.eq("order_id", query);
        }
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                wrapper
        );
        // 封装返回结果
        OrdersListPageVO resp = new OrdersListPageVO();
        resp.setPagenum((int) page.getCurrent());
        resp.setPagesize((int) page.getSize());
        resp.setTotal((int) page.getTotal());
        resp.setOrders(page.getRecords().stream().map(orderEntity -> convertOrderEntity2OrderItem(orderEntity)).collect(Collectors.toList()));
        return resp;
    }

    @Override
    public OrderVO getOne(Integer orderId) {
        OrderEntity orderEntity = this.getById(orderId);
        if (orderEntity == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "所选订单不存在");
        }
        OrderVO orderVO = new OrderVO();
        // 1.订单基本信息
        CompletableFuture<Void> orderInfoTask = CompletableFuture.runAsync(() -> {
            buildOrderVOBaseInfo(orderVO, orderEntity);
        }, executor);
        // 2.订单项信息
        CompletableFuture<Void> orderItemTask = CompletableFuture.runAsync(() -> {
            List<OrderGoodsEntity> goods = orderGoodsService.listByOrderId(orderId);
            if (!CollectionUtils.isEmpty(goods)) {
                buildOrderVOGoodsItemInfo(orderVO, goods);
            }
        }, executor);
        try {
            CompletableFuture.allOf(orderItemTask).get();
        } catch (Exception e) {
            log.error("线程池异步任务失败：", e);
            throw new BizException(BizCodeEnum.INTERNAL_SERVER_ERROR, "获取订单详情失败");
        }
        return orderVO;
    }

    @Override
    public void removeCascade(Integer orderId) {
        this.removeById(orderId);
        orderGoodsService.removeByOrderId(orderId);
    }

    @Override
    public OrderVO updateById(Integer orderId, OrderUpdateVO updateVO) {
        OrderEntity orderEntity = this.getById(orderId);
        if (orderEntity == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "所选订单不存在");
        }
        OrderEntity updateOrder = new OrderEntity();
        updateOrder.setOrderId(orderId);
        boolean needUpdate = false;
        // 增量更新
        if (updateVO.getOrder_price() != null) {
            updateOrder.setOrderPrice(updateVO.getOrder_price());
            needUpdate = true;
        }
        if (updateVO.getIs_send() != null) {
            switch (updateVO.getIs_send()) {
                case 1:
                    updateOrder.setIsSend("是"); break;
                case 0:
                    updateOrder.setIsSend("否"); break;
                default:
                    throw new BizException(BizCodeEnum.BAD_REQUEST, "非法参数值: is_send=" + updateVO.getIs_send());
            }
            needUpdate = true;
        }
        if (updateVO.getPay_status() != null) {
            switch (updateVO.getPay_status()) {
                case 1:
                    updateOrder.setPayStatus("已付款"); break;
                case 0:
                    updateOrder.setPayStatus("未付款"); break;
                default:
                    throw new BizException(BizCodeEnum.BAD_REQUEST, "非法参数值: pay_status=" + updateVO.getPay_status());
            }
            needUpdate = true;
        }
        if (updateVO.getPay_status() != null && updateVO.getPay_status() == 1) {
            Integer payType = updateVO.getOrder_pay();
            if (payType != null) {
                switch (payType) {
                    case 0:
                        updateOrder.setOrderPay("未付款"); break;
                    case 1:
                        updateOrder.setOrderPay("支付宝"); break;
                    case 2:
                        updateOrder.setOrderPay("微信"); break;
                    case 3:
                        updateOrder.setOrderPay("银行卡"); break;
                    default:
                        throw new BizException(BizCodeEnum.BAD_REQUEST, "非法参数值: order_pay=" + payType);
                }
                needUpdate = true;
            }

        }
        // 更新
        if (needUpdate) {
            this.updateById(updateOrder);
        }
        // 返回
        return getOne(orderId);
    }

    private OrdersListPageVO.Orders convertOrderEntity2OrderItem(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }
        OrdersListPageVO.Orders order = new OrdersListPageVO.Orders();
        order.setOrder_id(orderEntity.getOrderId());
        order.setUser_id(orderEntity.getUserId());
        order.setOrder_number(orderEntity.getOrderNumber());
        order.setOrder_price(orderEntity.getOrderPrice());
        order.setOrder_pay(orderEntity.getOrderPay());
        order.setIs_send(orderEntity.getIsSend());
        order.setTrade_no(orderEntity.getTradeNo());
        order.setOrder_fapiao_title(orderEntity.getOrderFapiaoTitle());
        order.setOrder_fapiao_company(orderEntity.getOrderFapiaoCompany());
        order.setOrder_fapiao_content(orderEntity.getOrderFapiaoContent());
        order.setConsignee_addr(orderEntity.getConsigneeAddr());
        order.setPay_status(orderEntity.getPayStatus());
        order.setCreate_time(orderEntity.getCreateTime());
        order.setUpdate_time(orderEntity.getUpdateTime());
        return order;
    }

    private void buildOrderVOBaseInfo(OrderVO order, OrderEntity orderEntity) {
        order.setOrder_id(orderEntity.getOrderId());
        order.setUser_id(orderEntity.getUserId());
        order.setOrder_number(orderEntity.getOrderNumber());
        order.setOrder_price(orderEntity.getOrderPrice());
        order.setOrder_pay(orderEntity.getOrderPay());
        order.setIs_send(orderEntity.getIsSend());
        order.setTrade_no(orderEntity.getTradeNo());
        order.setOrder_fapiao_title(orderEntity.getOrderFapiaoTitle());
        order.setOrder_fapiao_company(orderEntity.getOrderFapiaoCompany());
        order.setOrder_fapiao_content(orderEntity.getOrderFapiaoContent());
        order.setConsignee_addr(orderEntity.getConsigneeAddr());
        order.setPay_status(orderEntity.getPayStatus());
        order.setCreate_time(orderEntity.getCreateTime());
        order.setUpdate_time(orderEntity.getUpdateTime());
    }

    private void buildOrderVOGoodsItemInfo(OrderVO order, List<OrderGoodsEntity> orderGoodsEntities) {
        order.setGoods(orderGoodsEntities.stream().map(goods -> {
            OrderVO.Goods orderGoods = new OrderVO.Goods();
            orderGoods.setOrder_id(order.getOrder_id());
            orderGoods.setGoods_id(goods.getGoodsId());
            orderGoods.setGoods_price(goods.getGoodsPrice());
            orderGoods.setGoods_number(goods.getGoodsNumber());
            orderGoods.setGoods_total_price(goods.getGoodsTotalPrice());
            return orderGoods;
        }).collect(Collectors.toList()));
    }

}