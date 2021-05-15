package com.vivi.vue.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@Data
@TableName("sp_role")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	@TableId
	private Integer roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 权限ids,1,2,5
	 */
	private String psIds;
	/**
	 * 控制器-操作,控制器-操作,控制器-操作
	 */
	private String psCa;
	/**
	 * 角色描述
	 */
	private String roleDesc;

}
