package com.vivi.vue.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.entity.GoodsAttrEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品-属性关联表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface GoodsAttrService extends IService<GoodsAttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据商品id列表查询
     * @param goodsId
     * @return
     */
    List<GoodsAttrEntity> listByGoodsId(Integer goodsId);

    /**
     * 删除指定商品id关联的记录
     * @param goodsId
     * @return
     */
    boolean removeByGoodsId(Integer goodsId);

    /**
     * 指定指定商品指定属性值
     * @param goodsId
     * @param attrId
     * @param attrValue
     */
    int saveOrUpdateGoodsAttr(Integer goodsId, Integer attrId, String attrValue);
}

