package com.vivi.vue.shop.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wangwei
 * 2021/2/10 18:17
 *
 * 单个商品的详细信息
 */
@Data
public class GoodsVO {

    /**
     *          "goods_id": 145,
     *         "goods_name": "test_goods_name2",
     *         "goods_price": 20,
     *         "goods_number": 30,
     *         "goods_weight": 40,
     *         "goods_introduce": "abc",
     *         "goods_big_logo": "",
     *         "goods_small_logo": "",
     *         "goods_state": 1,
     *         "add_time": 1512962370,
     *         "upd_time": 1512962370,
     *         "hot_mumber": 0,
     *         "is_promote": false,
     *         "pics": [
     *             {
     *                 "pics_id": 397,
     *                 "goods_id": 145,
     *                 "pics_big": "uploads/goodspics/big_30f08d52c551ecb447277eae232304b8",
     *                 "pics_mid": "uploads/goodspics/mid_30f08d52c551ecb447277eae232304b8",
     *                 "pics_sma": "uploads/goodspics/sma_30f08d52c551ecb447277eae232304b8"
     *             }
     *         ],
     *         "attrs": [
     *             {
     *                 "goods_id": 145,
     *                 "attr_id": 15,
     *                 "attr_value": "ddd",
     *                 "add_price": null,
     *                 "attr_name": "fffffff",
     *                 "attr_sel": "many",
     *                 "attr_write": "list",
     *                 "attr_vals": ""
     *             },
     *             {
     *                 "goods_id": 145,
     *                 "attr_id": 15,
     *                 "attr_value": "eee",
     *                 "add_price": null,
     *                 "attr_name": "fffffff",
     *                 "attr_sel": "many",
     *                 "attr_write": "list",
     *                 "attr_vals": ""
     *             }
     *         ]
     */



    /**
     * goods_id : 145
     * goods_name : test_goods_name2
     * goods_price : 20
     * goods_number : 30
     * goods_weight : 40
     * goods_introduce : abc
     * goods_big_logo :
     * goods_small_logo :
     * goods_state : 1
     * add_time : 1512962370
     * upd_time : 1512962370
     * hot_mumber : 0
     * is_promote : false
     * pics : [{"pics_id":397,"goods_id":145,"pics_big":"uploads/goodspics/big_30f08d52c551ecb447277eae232304b8","pics_mid":"uploads/goodspics/mid_30f08d52c551ecb447277eae232304b8","pics_sma":"uploads/goodspics/sma_30f08d52c551ecb447277eae232304b8"}]
     * attrs : [{"goods_id":145,"attr_id":15,"attr_value":"ddd","add_price":null,"attr_name":"fffffff","attr_sel":"many","attr_write":"list","attr_vals":""},{"goods_id":145,"attr_id":15,"attr_value":"eee","add_price":null,"attr_name":"fffffff","attr_sel":"many","attr_write":"list","attr_vals":""}]
     */

    private Integer goods_id;
    private String goods_name;
    private BigDecimal goods_price;
    private Integer goods_number;
    private Integer goods_weight;
    private String goods_introduce;
    private String goods_big_logo;
    private String goods_small_logo;
    private Integer goods_state;
    private Date add_time;
    private Date upd_time;
    private Boolean is_promote;
    private Integer hot_number;
    private List<Pic> pics;
    private List<Attr> attrs;

    @Data
    public static class Pic {
        /**
         * pics_id : 397
         * goods_id : 145
         * pics_big : uploads/goodspics/big_30f08d52c551ecb447277eae232304b8
         * pics_mid : uploads/goodspics/mid_30f08d52c551ecb447277eae232304b8
         * pics_sma : uploads/goodspics/sma_30f08d52c551ecb447277eae232304b8
         */

        private Integer pics_id;
        private Integer goods_id;
        private String pics_big;
        private String pics_mid;
        private String pics_sma;

    }

    @Data
    public static class Attr {
        /**
         * goods_id : 145
         * attr_id : 15
         * attr_value : ddd
         * add_price : null
         * attr_name : fffffff
         * attr_sel : many
         * attr_write : list
         * attr_vals :
         */

        private Integer goods_id;
        private Integer attr_id;
        /*此商品对应的此项参数的值*/
        private String attr_value;
        private BigDecimal add_price;
        /*此参数的基本信息*/
        private String attr_name;
        /*only或many*/
        private String attr_sel;
        private String attr_write;
        /*此参数的可取值*/
        private String attr_vals;

    }

}
