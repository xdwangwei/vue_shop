package com.vivi.vue.shop.vo;

import lombok.Data;

/**
 * @author wangwei
 * 2021/2/9 22:58
 *
 * 商品分类，分页查询
 */
@Data
public class CategoryPageQueryVO {

    // type[1,2,3]值：1，2，3 分别表示显示一层二层三层分类列表<br />【可选参数】如果不传递，则默认获取所有级别的分类
    // pagenum当前页码值【可选参数】如果不传递，则默认获取所有分类
    // pagesize每页显示多少条数据【可选参数】如果不传递，则默认获取所有分类

    private Integer type;

    private Integer pagenum;

    private Integer pagesize;
}
