package com.vivi.vue.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.entity.GoodsPicsEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品-相册关联表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface GoodsPicsService extends IService<GoodsPicsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据商品id查询列表
     * @param goodsId
     * @return
     */
    List<GoodsPicsEntity> listByGoodsId(Integer goodsId);

    /**
     * 删除指定商品id关联的记录
     * @param goodsId
     * @return
     */
    boolean removeByGoodsId(Integer goodsId);
}

