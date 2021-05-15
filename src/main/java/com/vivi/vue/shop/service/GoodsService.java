package com.vivi.vue.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.entity.GoodsEntity;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.vo.*;

import java.util.Map;

/**
 * 商品表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 分页查询商品列表
     * @param pageQueryVO
     * @return
     */
    GoodsListPageVO queryPage(PageQueryVO pageQueryVO);

    /**
     * 新增商品
     * @param goods
     * @return
     */
    GoodsVO add(GoodsAddVO goods);

    /**
     * 获取指定商品详情
     * @param goodsId
     * @return
     */
    GoodsVO getOne(Integer goodsId);

    /**
     * 删除商品及其相关联的图片和参数信息
     * @param goodsId
     */
    void removeCascadeById(Integer goodsId);

    /**
     * 更新指定商品信息
     * @param updateVO
     * @return
     */
    GoodsVO updateById(Integer goodsId, GoodsUpdateVO updateVO);
}

