package com.vivi.vue.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.entity.CategoryEntity;
import com.vivi.vue.shop.vo.CategoryPageQueryVO;
import com.vivi.vue.shop.vo.CategoryTreeVO;

/**
 * 
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface CategoryService extends IService<CategoryEntity> {


    /**
     * 分页、按条件查询商品分类
     * 如果未传分页参数，直接返回列表
     * 如果传了分页参数，则返回分页数据模型
     * @param pageQueryVO
     * @return
     */
    Object listOrPage(CategoryPageQueryVO pageQueryVO);

    /**
     * 新增分类
     * @param catPid
     * @param catName
     * @param catLevel
     * @return
     */
    CategoryTreeVO add(Integer catPid, String catName, Integer catLevel);

    /***
     * 查询指定分类信息
     * @param catId
     * @return
     */
    CategoryTreeVO getOne(Integer catId);

    /**
     * 更新分类名称
     * @param catId
     * @param jsonObject
     * @return
     */
    CategoryTreeVO updateCategory(Integer catId, JSONObject jsonObject);
}

