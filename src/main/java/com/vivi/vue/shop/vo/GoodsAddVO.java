package com.vivi.vue.shop.vo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangwei
 * 2021/2/10 18:32
 *
 * 新增商品
 */
@Data
public class GoodsAddVO {

    /**
     * {
     *   "goods_name":"test_goods_name2",
     *   "goods_cat": "1,2,3",
     *   "goods_price":20,
     *   "goods_number":30,
     *   "goods_weight":40,
     *   "goods_introduce":"abc",
     *   "pics":[
     *     {"pic":"/tmp_uploads/30f08d52c551ecb447277eae232304b8"}
     *     ],
     *   "attrs":[
     *     {
     *       "attr_id":15,
     *       "attr_value":"ddd"
     *     },
     *     {
     *       "attr_id":15,
     *       "attr_value":"eee"
     *     }
     *     ]
     * }
     */
    @NotBlank(message = "参数 goods_name 不能为空")
    private String goods_name;

    // 以','分割的分类列表
    @NotBlank(message = "参数 goods_cat 不能为空")
    @Pattern(regexp = "^\\d+,\\d+,\\d+$", message = "参数 goods_cat 为以逗号分隔的各级分类id组成的字符串")
    private String goods_cat;

    @NotNull(message = "参数 goods_price 不能为空")
    private BigDecimal goods_price;

    @NotNull(message = "参数 goods_number 不能为空")
    @Min(value = 0, message = "参数 goods_number 最小值为0")
    private Integer goods_number;

    @NotNull(message = "参数 goods_weight 不能为空")
    @Min(value = 0, message = "参数 goods_weight 最小值为0")
    private Integer goods_weight;

    private String goods_introduce;

    // 上传的图片临时路径（对象）
    @Valid
    private List<Pic> pics;
    // 商品的参数（数组），包含 `动态参数` 和 `静态属性`
    @Valid
    private List<Attr> attrs;

    @Data
    public static class Pic {

        @NotBlank(message = "参数pics.pic不能为空")
        String pic;
    }

    @Data
    public static class Attr {
        @NotNull(message = "参数attrs.attr_id不能为空")
        @Min(value = 1, message = "请输入有效的attrs.attr_id")
        Integer attr_id;

        @NotBlank(message = "参数attrs.attr_value不能为空")
        String attr_value;
    }
}
