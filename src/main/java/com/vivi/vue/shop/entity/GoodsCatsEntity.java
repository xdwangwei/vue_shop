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
@TableName("sp_goods_cats")
public class GoodsCatsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
	@TableId
	private Integer catId;
	/**
	 * 父级id
	 */
	private Integer parentId;
	/**
	 * 分类名称
	 */
	private String catName;
	/**
	 * 是否显示
	 */
	private Integer isShow;
	/**
	 * 分类排序
	 */
	private Integer catSort;
	/**
	 * 数据标记
	 */
	private Integer dataFlag;
	/**
	 * 创建时间
	 */
	private Integer createTime;

}
