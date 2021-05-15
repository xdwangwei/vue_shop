package com.vivi.vue.shop.vo;

import lombok.Data;

/**
 * @author wangwei
 * 2021/2/10 13:12
 *
 * 分类参数
 */
@Data
public class CategoryAttributeVO {

    /**
     *             "attr_id": 1,
     *             "attr_name": "cpu",
     *             "cat_id": 22,
     *             "attr_sel": "only",
     *             "attr_write": "manual",
     *             "attr_vals": "ffff"
     */
    private Integer attr_id;

    private String attr_name;

    private Integer cat_id;

    /**
     * only:输入框(唯一) many:后台下拉列表/前台单选框
     */
    private String attr_sel;

    /**
     * manual:手工录入 list:从列表选择
     */
    private String attr_write;

    /**
     * 如果 attr_write:list,那么有值，该值以逗号分隔
     */
    private String attr_vals;
}
