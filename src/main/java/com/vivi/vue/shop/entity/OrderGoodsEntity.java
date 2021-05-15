package com.vivi.vue.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品订单关联表
 * 
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@Data
@TableName("sp_order_goods")
public class OrderGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 订单id
	 */
	private Integer orderId;
	/**
	 * 商品id
	 */
	private Integer goodsId;
	/**
	 * 商品单价
	 */
	private BigDecimal goodsPrice;
	/**
	 * 购买单个商品数量
	 */
	private Integer goodsNumber;
	/**
	 * 商品小计价格
	 */
	private BigDecimal goodsTotalPrice;

}
