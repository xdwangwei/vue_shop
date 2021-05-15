package com.vivi.vue.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理员表
 * 
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@Data
@TableName("sp_manager")
public class ManagerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer mgId;
	/**
	 * 名称
	 */
	private String mgName;
	/**
	 * 密码
	 */
	private String mgPwd;
	/**
	 * 注册时间
	 */
	private Date mgTime;
	/**
	 * 角色id
	 */
	private Integer roleId;
	/**
	 * 
	 */
	private String mgMobile;
	/**
	 * 
	 */
	private String mgEmail;
	/**
	 * 1：表示启用 0:表示禁用
	 */
	private Integer mgState;

}
