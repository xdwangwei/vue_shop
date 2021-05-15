package com.vivi.vue.shop.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author wangwei
 * 2021/2/10 13:33
 *
 * 新增规格参数
 */
@Data
public class AttributeAddVO {

    @NotBlank(message = "参数attr_name不能为空")
    private String attr_name;

    // [only,many]
    @NotNull(message = "参数attr_sel不能为空")
    @Pattern(regexp = "^\\bonly\\b|\\bmany\\b$", message = "参数attr_sel可选值为only或many")
    private String attr_sel;

    // 如果 sttr_sel 是 many 就需要填写值的选项，以逗号分隔【可选参数】
    private String attr_vals = "";
}
