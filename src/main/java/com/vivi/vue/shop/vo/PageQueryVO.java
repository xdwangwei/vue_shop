package com.vivi.vue.shop.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author wangwei
 * 2021/2/9 11:36
 *
 * 分页查询请求参数
 */
@Data
public class PageQueryVO {

    // 关键字
    private String query;

    // 页码
    @NotNull(message = "pagenum必须填写")
    @Min(value = 1, message = "指定页码不能小于1")
    private Integer pagenum;

    // 每页大小
    @NotNull(message = "pagesize必须填写")
    @Min(value = 1, message = "每页显示记录数最少为1")
    private Integer pagesize;
}
