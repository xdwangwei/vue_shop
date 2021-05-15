package com.vivi.vue.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangwei
 * 2021/2/11 14:38
 *
 * 订单详情
 */
@Data
public class OrderVO {

    private Integer order_id;
    private Integer user_id;
    private String order_number;
    private BigDecimal order_price;
    /**
     * 支付方式  未支付 支付宝  微信  银行卡
     */
    private String order_pay;
    private String is_send;
    private String trade_no;
    private String order_fapiao_title;
    private String order_fapiao_company;
    private String order_fapiao_content;
    /**
     * consignee收货人地址
     */
    private String consignee_addr;
    /**
     * 订单状态：未付款、已付款
     */
    private String pay_status;
    private Date create_time;
    private Date update_time;

    // 订单项
    private List<Goods> goods = new ArrayList<>();

    @Data
    public static class Goods {
        /**
         * {
         *                 "id": 82,
         *                 "order_id": 67,
         *                 "goods_id": 96,
         *                 "goods_price": 333,
         *                 "goods_number": 2,
         *                 "goods_total_price": 999
         *             }
         */
        private Integer order_id;

        private Integer goods_id;

        private BigDecimal goods_price;

        private Integer goods_number;

        private BigDecimal goods_total_price;

    }
}
