package com.vivi.vue.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wangwei
 * 2021/2/10 17:53
 *
 * 分页查询商品列表返回对象
 */
@Data
public class GoodsListPageVO {

    // 当前页码
    private Integer pagenum;

    // 页大小
    private Integer pagesize;

    // 总记录数
    private Integer total;

    // 当前页所有记录
    private List<Goods> goods;

    @Data
    public static class Goods {
        /**
         * goods_id商品 ID
         * goods_name商品名称
         * goods_price价格
         * goods_number数量
         * goods_weight重量不能为空
         * goods_state商品状态商品状态 0: 未通过 1: 审核中 2: 已审核
         * add_time添加时间
         * upd_time更新时间
         * hot_mumber热销品数量
         * is_promote是否是热销品
         */

        /**
         * 主键id
         */
        private Integer goods_id;
        /**
         * 商品名称
         */
        private String goods_name;
        /**
         * 商品价格
         */
        private BigDecimal goods_price;
        /**
         * 商品数量
         */
        private Integer goods_number;
        /**
         * 商品重量
         */
        private Integer goods_weight;
        /**
         * 商品状态 0: 未通过 1: 审核中 2: 已审核
         */
        private Integer goods_state;
        /**
         * 添加商品时间
         */
        private Date add_time;
        /**
         * 修改商品时间
         */
        private Date upd_time;
        /**
         * 热卖数量
         */
        private Integer hot_number;
        /**
         * 是否促销
         */
        private Boolean is_promote;
    }
}
