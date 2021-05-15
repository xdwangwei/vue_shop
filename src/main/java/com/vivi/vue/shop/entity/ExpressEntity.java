package com.vivi.vue.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 快递表
 * 
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@Data
@TableName("sp_express")
public class ExpressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer expressId;
	/**
	 * 订单id
	 */
	private Integer orderId;
	/**
	 * 订单快递公司名称
	 */
	private String expressCom;
	/**
	 * 快递单编号
	 */
	private String expressNu;
	/**
	 * 记录生成时间
	 */
	private Integer createTime;
	/**
	 * 记录修改时间
	 */
	private Integer updateTime;

}
