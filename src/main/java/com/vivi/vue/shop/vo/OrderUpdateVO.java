package com.vivi.vue.shop.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wangwei
 * 2021/2/11 15:02
 *
 * 修改订单信息
 *
 * 所有请求数据都是增量更新，如果参数不填写，就不会更新该字段
 */
@Data
public class OrderUpdateVO {

    /**
     * is_send订单是否发货1:已经发货，0:未发货
     * order_pay订单支付支付方式 0 未支付 1 支付宝 2 微信 3 银行卡
     * order_price订单价格
     * pay_status支付状态订单状态： 0 未付款、1 已付款
     */
    // @NotNull(message = "请求参数 is_send 不能为空")
    // @Min(value = 0, message = "is_send 只能取 0 或 1")
    // @Max(value = 0, message = "is_send 只能取 0 或 1")
    private Integer is_send;

    // @NotNull(message = "请求参数 order_pay 不能为空")
    // @Min(value = 0, message = "order_pay 只能取 0,1,2,3")
    // @Max(value = 3, message = "order_pay 只能取 0,1,2,3")
    private Integer order_pay;

    // @NotNull(message = "请求参数 order_price 不能为空")
    private BigDecimal order_price;

    // @NotNull(message = "请求参数 pay_status 不能为空")
    // @Min(value = 0, message = "pay_status 只能为 0 或 1")
    // @Max(value = 0, message = "pay_status 只能为 0 或 1")
    private Integer pay_status;
}
