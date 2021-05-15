package com.vivi.vue.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 收货人表
 * 
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@Data
@TableName("sp_consignee")
public class ConsigneeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer cgnId;
	/**
	 * 会员id
	 */
	private Integer userId;
	/**
	 * 收货人名称
	 */
	private String cgnName;
	/**
	 * 收货人地址
	 */
	private String cgnAddress;
	/**
	 * 收货人电话
	 */
	private String cgnTel;
	/**
	 * 邮编
	 */
	private String cgnCode;
	/**
	 * 删除时间
	 */
	private Integer deleteTime;

}
