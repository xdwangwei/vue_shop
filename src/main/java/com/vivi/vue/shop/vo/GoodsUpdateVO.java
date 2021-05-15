package com.vivi.vue.shop.vo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangwei
 * 2021/2/11 8:24
 *
 * 商品修改数据模型
 */
@Data
public class GoodsUpdateVO {

    @NotBlank(message = "参数 goods_name 不能为空")
    private String goods_name;

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


    /**
     * [{"pics_id": 1,
     *     	"pic":"/tmp_uploads/30f08d52c551ecb447277eae232304b8"}]
     */
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
