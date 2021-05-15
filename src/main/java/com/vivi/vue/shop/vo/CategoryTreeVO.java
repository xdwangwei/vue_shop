package com.vivi.vue.shop.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author wangwei
 * 2021/2/9 23:02
 *
 * 商品分类树形结构
 */
@Data
public class CategoryTreeVO {

    /**
     * "cat_id": 1,
     *             "cat_name": "大家电",
     *             "cat_pid": 0,
     *             "cat_level": 0,
     *             "cat_deleted": false,
     *             "children": [
     */
    private Integer cat_id;

    private String cat_name;

    private Integer cat_pid;

    private Integer cat_level;

    private boolean cat_deleted;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<CategoryTreeVO> children;
}
