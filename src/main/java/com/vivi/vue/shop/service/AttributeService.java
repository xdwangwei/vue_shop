package com.vivi.vue.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.entity.AttributeEntity;
import com.vivi.vue.shop.vo.AttributeAddVO;
import com.vivi.vue.shop.vo.CategoryAttributeVO;

import java.util.List;
import java.util.Map;

/**
 * 属性表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface AttributeService extends IService<AttributeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询指定分类下执行类型的参数列表
     * @param catId
     * @param sel
     * @return
     */
    List<CategoryAttributeVO> listByCatIdAndSelType(Integer catId, String sel);

    /**
     * 为指定分类新增参数
     * @param catId
     * @param addVO
     * @return
     */
    CategoryAttributeVO add(Integer catId, AttributeAddVO addVO);

    /**
     * 获取指定分类信息
     * @param attrId
     * @return
     */
    CategoryAttributeVO getOne(Integer attrId);

    /**
     * 修改指定参数信息
     * @param attrId
     * @param attrVO
     * @return
     */
    CategoryAttributeVO update(Integer attrId, AttributeAddVO attrVO);
}

