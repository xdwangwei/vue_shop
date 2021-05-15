package com.vivi.vue.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wangwei
 * 2021/2/10 17:53
 *
 * 分页查询订单列表返回对象
 */
@Data
public class OrdersListPageVO {

    // 当前页码
    private Integer pagenum;

    // 页大小
    private Integer pagesize;

    // 总记录数
    private Integer total;

    // 当前页所有记录
    private List<Orders> orders;

    @Data
    public static class Orders {
        /**
         * order_id : 47
         * user_id : 133
         * order_number : itcast-59e7502d7993d
         * order_price : 322
         * order_pay : 1
         * is_send : 是
         * trade_no :
         * order_fapiao_title : 个人
         * order_fapiao_company :
         * order_fapiao_content : 办公用品
         * consignee_addr : a:7:{s:6:"cgn_id";i:1;s:7:"user_id";i:133;s:8:"cgn_name";s:9:"王二柱";s:11:"cgn_address";s:51:"北京市海淀区苏州街长远天地大厦305室";s:7:"cgn_tel";s:11:"13566771298";s:8:"cgn_code";s:6:"306810";s:11:"delete_time";N;}
         * pay_status : 1
         * create_time : 1508331565
         * update_time : 1508331565
         */

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

    }
}
