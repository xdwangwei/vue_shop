package com.vivi.vue.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 * 
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@Data
@TableName("sp_order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer orderId;
	/**
	 * 下订单会员id
	 */
	private Integer userId;
	/**
	 * 订单编号
	 */
	private String orderNumber;
	/**
	 * 订单总金额
	 */
	private BigDecimal orderPrice;
	/**
	 * 支付方式  '未支付' '支付宝'  '微信'  '银行卡'
	 */
	private String orderPay;
	/**
	 * 订单是否已经发货 [是，否]
	 */
	private String isSend;
	/**
	 * 支付宝交易流水号码
	 */
	private String tradeNo;
	/**
	 * 发票抬头 [个人,公司]
	 */
	private String orderFapiaoTitle;
	/**
	 * 公司名称
	 */
	private String orderFapiaoCompany;
	/**
	 * 发票内容
	 */
	private String orderFapiaoContent;
	/**
	 * consignee收货人地址
	 */
	private String consigneeAddr;
	/**
	 * 订单状态：'未付款'、'已付款'
	 */
	private String payStatus;
	/**
	 * 记录生成时间
	 */
	private Date createTime;
	/**
	 * 记录修改时间
	 */
	private Date updateTime;

}
