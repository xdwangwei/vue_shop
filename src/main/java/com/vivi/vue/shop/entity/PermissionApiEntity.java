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
@TableName("sp_permission_api")
public class PermissionApiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * permission_id
	 */
	private Integer psId;
	/**
	 * permission对应service
	 */
	private String psApiService;
	/**
	 * permission对应service的方法
	 */
	private String psApiAction;
	/**
	 * permission对应访问路径
	 */
	private String psApiPath;
	/**
	 * permission的排序
	 */
	private Integer psApiOrder;

}
