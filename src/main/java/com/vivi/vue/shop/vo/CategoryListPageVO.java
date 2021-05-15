package com.vivi.vue.shop.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wangwei
 * 2021/2/10 11:31
 *
 * 分页查询分类，数据模型
 */
@Data
public class CategoryListPageVO {

    // 当前页码
    private Integer pagenum;

    // 页大小
    private Integer pagesize;

    // 总记录数
    private Integer total;

    // 当前页所有记录
    private List<CategoryTreeVO> result;
}
