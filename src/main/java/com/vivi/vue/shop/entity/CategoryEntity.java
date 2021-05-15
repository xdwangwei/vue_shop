package com.vivi.vue.shop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@Data
@TableName("sp_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类唯一ID
	 */
	@TableId
	private Integer catId;
	/**
	 * 分类名称
	 */
	private String catName;
	/**
	 * 分类父ID
	 */
	private Integer catPid;
	/**
	 * 分类层级 0: 顶级 1:二级 2:三级
	 */
	private Integer catLevel;
	/**
	 * 是否删除 1为删除
	 */
	private Integer catDeleted;
	/**
	 * 
	 */
	private String catIcon;
	/**
	 * 
	 */
	private String catSrc;

}
