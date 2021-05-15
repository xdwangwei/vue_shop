package com.vivi.vue.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品-相册关联表
 * 
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@Data
@TableName("sp_goods_pics")
public class GoodsPicsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer picsId;
	/**
	 * 商品id
	 */
	private Integer goodsId;
	/**
	 * 相册大图800*800
	 */
	private String picsBig;
	/**
	 * 相册中图350*350
	 */
	private String picsMid;
	/**
	 * 相册小图50*50
	 */
	private String picsSma;

}
