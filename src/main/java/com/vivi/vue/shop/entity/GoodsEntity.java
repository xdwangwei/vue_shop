package com.vivi.vue.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 * 
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@Data
@TableName("sp_goods")
public class GoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer goodsId;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品价格
	 */
	private BigDecimal goodsPrice;
	/**
	 * 商品数量
	 */
	private Integer goodsNumber;
	/**
	 * 商品重量
	 */
	private Integer goodsWeight;
	/**
	 * 类型id
	 */
	private Integer catId;
	/**
	 * 商品详情介绍
	 */
	private String goodsIntroduce;
	/**
	 * 图片logo大图
	 */
	private String goodsBigLogo;
	/**
	 * 图片logo小图
	 */
	private String goodsSmallLogo;
	/**
	 * '是' 、 '否'
	 */
	private String isDel;
	/**
	 * 添加商品时间
	 */
	private Date addTime;
	/**
	 * 修改商品时间
	 */
	private Date updTime;
	/**
	 * 软删除标志字段
	 */
	private Date deleteTime;
	/**
	 * 一级分类id
	 */
	private Integer catOneId;
	/**
	 * 二级分类id
	 */
	private Integer catTwoId;
	/**
	 * 三级分类id
	 */
	private Integer catThreeId;
	/**
	 * 热卖数量
	 */
	private Integer hotNumber;
	/**
	 * 是否促销
	 */
	private Integer isPromote;
	/**
	 * 商品状态 0: 未通过 1: 审核中 2: 已审核
	 */
	private Integer goodsState;

}
