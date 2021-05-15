package com.vivi.vue.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限表
 * 
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@Data
@TableName("sp_permission")
public class PermissionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer psId;
	/**
	 * 权限名称
	 */
	private String psName;
	/**
	 * 父id
	 */
	private Integer psPid;
	/**
	 * 控制器
	 */
	private String psC;
	/**
	 * 操作方法
	 */
	private String psA;
	/**
	 * 权限等级 '0','1','2' 默认 '0'
	 */
	private Integer psLevel;

}
