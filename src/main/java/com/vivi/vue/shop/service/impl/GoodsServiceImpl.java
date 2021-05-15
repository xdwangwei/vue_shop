package com.vivi.vue.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.dao.GoodsDao;
import com.vivi.vue.shop.entity.AttributeEntity;
import com.vivi.vue.shop.entity.GoodsAttrEntity;
import com.vivi.vue.shop.entity.GoodsEntity;
import com.vivi.vue.shop.entity.GoodsPicsEntity;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.exception.BizException;
import com.vivi.vue.shop.service.AttributeService;
import com.vivi.vue.shop.service.GoodsAttrService;
import com.vivi.vue.shop.service.GoodsPicsService;
import com.vivi.vue.shop.service.GoodsService;
import com.vivi.vue.shop.utils.Constant;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;
import com.vivi.vue.shop.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, GoodsEntity> implements GoodsService {

    @Autowired
    AttributeService attributeService;

    @Autowired
    GoodsPicsService goodsPicsService;

    @Autowired
    GoodsAttrService goodsAttrService;

    @Autowired
    ThreadPoolExecutor executor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsEntity> page = this.page(
                new Query<GoodsEntity>().getPage(params),
                new QueryWrapper<GoodsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public GoodsListPageVO queryPage(PageQueryVO pageQueryVO) {

        // 构建分页查询参数
        Map<String, Object> params = new HashMap<>();
        params.put(Constant.PAGE, pageQueryVO.getPagenum().toString());
        params.put(Constant.LIMIT, pageQueryVO.getPagesize().toString());
        // 分页查询
        QueryWrapper<GoodsEntity> wrapper = new QueryWrapper<>();
        // 判断请求参数，可以传id或name
        String query = pageQueryVO.getQuery();
        if (!StringUtils.isEmpty(query)) {
            wrapper.like("goods_name", query);
        }
        IPage<GoodsEntity> page = this.page(
                new Query<GoodsEntity>().getPage(params),
                wrapper
        );
        // 封装返回结果
        GoodsListPageVO resp = new GoodsListPageVO();
        resp.setPagenum((int) page.getCurrent());
        resp.setPagesize((int) page.getSize());
        resp.setTotal((int) page.getTotal());
        resp.setGoods(page.getRecords().stream().map(goodsEntity -> convertGoodsEntity2GoodsItem(goodsEntity)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 新增商品信息
     * @param addVO
     * @return
     */
    @Transactional
    @Override
    public GoodsVO add(GoodsAddVO addVO) {
        final Integer[] goodsId = new Integer[1];
        // 1.商品基本信息
        CompletableFuture<Void> GoodsInfoTask = CompletableFuture.runAsync(() -> {
            GoodsEntity goodsEntity = new GoodsEntity();
            goodsEntity.setGoodsName(addVO.getGoods_name());
            goodsEntity.setGoodsPrice(addVO.getGoods_price());
            goodsEntity.setGoodsNumber(addVO.getGoods_number());
            goodsEntity.setGoodsWeight(addVO.getGoods_weight());
            goodsEntity.setGoodsIntroduce(addVO.getGoods_introduce());
            String goodsCatStr = addVO.getGoods_cat();
            String[] split = goodsCatStr.split(",");
            Integer cat1 = Integer.parseInt(split[0]);
            Integer cat2 = Integer.parseInt(split[1]);
            Integer cat3 = Integer.parseInt(split[2]);
            goodsEntity.setCatId(cat3);
            goodsEntity.setCatOneId(cat1);
            goodsEntity.setCatTwoId(cat2);
            goodsEntity.setCatThreeId(cat3);
            goodsEntity.setIsDel("否");
            goodsEntity.setAddTime(new Date());
            try {
                this.save(goodsEntity);
                goodsId[0] = goodsEntity.getGoodsId();
            } catch (DuplicateKeyException e) {
                throw new BizException(BizCodeEnum.BAD_REQUEST, "参数goods_name指定名称已存在");
            }
        }, executor);
        // 2.商品图片信息
        CompletableFuture<Void> GoodsPicsTask = GoodsInfoTask.thenRunAsync(() -> {
            List<GoodsAddVO.Pic> pics = addVO.getPics();
            if (!CollectionUtils.isEmpty(pics)) {
                List<GoodsPicsEntity> picsEntities = pics.stream().map(pic -> {
                    GoodsPicsEntity picsEntity = new GoodsPicsEntity();
                    picsEntity.setGoodsId(goodsId[0]);
                    picsEntity.setPicsBig(pic.getPic());
                    picsEntity.setPicsMid(pic.getPic());
                    picsEntity.setPicsSma(pic.getPic());
                    return picsEntity;
                }).collect(Collectors.toList());
                goodsPicsService.saveBatch(picsEntities);
            }
        }, executor);
        // 3.商品参数
        CompletableFuture<Void> goodsAttrsTask = GoodsInfoTask.thenRunAsync(() -> {
            List<GoodsAddVO.Attr> attrs = addVO.getAttrs();
            if (!CollectionUtils.isEmpty(attrs)) {
                List<GoodsAttrEntity> attrEntities = attrs.stream().map(attr -> {
                    GoodsAttrEntity attrEntity = new GoodsAttrEntity();
                    attrEntity.setGoodsId(goodsId[0]);
                    attrEntity.setAttrId(attr.getAttr_id());
                    attrEntity.setAttrValue(attr.getAttr_value());
                    return attrEntity;
                }).collect(Collectors.toList());
                goodsAttrService.saveBatch(attrEntities);
            }
        }, executor);
        // 等待异步任务结束
        try {
            CompletableFuture.allOf(GoodsPicsTask, goodsAttrsTask).get();
        } catch (Exception e) {
            log.error("线程池异常：", e);
            throw new RuntimeException("新增商品失败，请重试");
        }
        // 构建返回信息
        return getOne(goodsId[0]);
    }

    @Override
    public GoodsVO getOne(Integer goodsId) {
        GoodsEntity goodsEntity = this.getById(goodsId);
        if (goodsEntity == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "指定商品不存在");
        }
        GoodsVO goodsVO = new GoodsVO();
        // 1.商品基本信息部分
        CompletableFuture<Void> GoodsInfoTask = CompletableFuture.runAsync(() -> {
            goodsVO.setGoods_id(goodsEntity.getGoodsId());
            goodsVO.setGoods_name(goodsEntity.getGoodsName());
            goodsVO.setGoods_price(goodsEntity.getGoodsPrice());
            goodsVO.setGoods_number(goodsEntity.getGoodsNumber());
            goodsVO.setGoods_weight(goodsEntity.getGoodsWeight());
            goodsVO.setGoods_introduce(goodsEntity.getGoodsIntroduce());
            goodsVO.setGoods_big_logo(goodsEntity.getGoodsBigLogo());
            goodsVO.setGoods_small_logo(goodsEntity.getGoodsSmallLogo());
            goodsVO.setGoods_state(goodsEntity.getGoodsState());
            goodsVO.setAdd_time(goodsEntity.getAddTime());
            goodsVO.setUpd_time(goodsEntity.getUpdTime());
            goodsVO.setHot_number(goodsEntity.getHotNumber());
            goodsVO.setIs_promote(goodsEntity.getIsPromote() == 1 ? true : false);
        }, executor);
        // 2.商品pics信息
        CompletableFuture<Void> GoodsPicsTask = CompletableFuture.runAsync(() -> {
            List<GoodsPicsEntity> goodsPicsEntities = goodsPicsService.listByGoodsId(goodsId);
            if (!CollectionUtils.isEmpty(goodsPicsEntities)) {
                List<GoodsVO.Pic> pics = goodsPicsEntities.stream().map(picEntity -> {
                    GoodsVO.Pic pic = new GoodsVO.Pic();
                    pic.setGoods_id(picEntity.getGoodsId());
                    pic.setPics_id(picEntity.getPicsId());
                    pic.setPics_big(picEntity.getPicsBig());
                    pic.setPics_mid(picEntity.getPicsMid());
                    pic.setPics_sma(picEntity.getPicsSma());
                    return pic;
                }).collect(Collectors.toList());
                goodsVO.setPics(pics);
            }
        }, executor);
        // 3.商品attrs信息
        CompletableFuture<Void> GoodsAttrsTask = CompletableFuture.runAsync(() -> {
            List<GoodsAttrEntity> goodsAttrEntities = goodsAttrService.listByGoodsId(goodsId);
            if (!CollectionUtils.isEmpty(goodsAttrEntities)) {
                List<GoodsVO.Attr> attrs = goodsAttrEntities.stream().map(attrEntity -> {
                    GoodsVO.Attr attr = new GoodsVO.Attr();
                    attr.setGoods_id(goodsId);
                    attr.setAttr_id(attrEntity.getAttrId());
                    attr.setAttr_value(attrEntity.getAttrValue());
                    attr.setAdd_price(attrEntity.getAddPrice());
                    AttributeEntity attribute = attributeService.getById(attrEntity.getAttrId());
                    if (attribute != null) {
                        attr.setAttr_name(attribute.getAttrName());
                        attr.setAttr_sel(attribute.getAttrSel());
                        attr.setAttr_write(attribute.getAttrWrite());
                        attr.setAttr_vals(attribute.getAttrVals());
                    }
                    return attr;
                }).collect(Collectors.toList());
                goodsVO.setAttrs(attrs);
            }
        }, executor);
        // 等待异步任务结束
        try {
            CompletableFuture.allOf(GoodsInfoTask, GoodsPicsTask, GoodsAttrsTask).get();
        } catch (Exception e) {
            log.error("线程池任务异常：", e);
            throw new RuntimeException("获取商品详情失败");
        }
        return goodsVO;
    }

    @Override
    public void removeCascadeById(Integer goodsId) {
        boolean b = this.removeById(goodsId);
        boolean b1 = goodsPicsService.removeByGoodsId(goodsId);
        boolean b2 = goodsAttrService.removeByGoodsId(goodsId);
        return;
    }

    @Transactional
    @Override
    public GoodsVO updateById(Integer goodsId, GoodsUpdateVO updateVO) {
        GoodsEntity goodsEntity = this.getById(goodsId);
        if (goodsEntity == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "所选商品不存在");
        }
        // 1.更新商品基本信息
        CompletableFuture<Void> GoodsInfoTask = CompletableFuture.runAsync(() -> {
            GoodsEntity goodsUpdate = new GoodsEntity();
            goodsUpdate.setGoodsId(goodsId);
            goodsUpdate.setGoodsName(updateVO.getGoods_name());
            goodsUpdate.setGoodsNumber(updateVO.getGoods_number());
            goodsUpdate.setGoodsPrice(updateVO.getGoods_price());
            goodsUpdate.setGoodsWeight(updateVO.getGoods_weight());
            if (StringUtils.isNotBlank(updateVO.getGoods_introduce())) {
                goodsUpdate.setGoodsIntroduce(updateVO.getGoods_introduce());
            }
            this.updateById(goodsUpdate);
        }, executor);
        // 2.更新商品图片信息(不修改已有图片，直接新增记录)
        CompletableFuture<Void> GoodsPicsTask = CompletableFuture.runAsync(() -> {
            List<GoodsUpdateVO.Pic> pics = updateVO.getPics();
            if (!CollectionUtils.isEmpty(pics)) {
                List<GoodsPicsEntity> collect = pics.stream().map(pic -> {
                    GoodsPicsEntity entity = new GoodsPicsEntity();
                    entity.setGoodsId(goodsId);
                    entity.setPicsBig(pic.getPic());
                    entity.setPicsMid(pic.getPic());
                    entity.setPicsSma(pic.getPic());
                    return entity;
                }).collect(Collectors.toList());
                goodsPicsService.saveBatch(collect);
            }
        }, executor);
        // 3.更新商品参数信息(如果已有就修改，否则新增)
        CompletableFuture<Void> GoodsAttrsTask = CompletableFuture.runAsync(() -> {
            List<GoodsUpdateVO.Attr> attrs = updateVO.getAttrs();
            if (!CollectionUtils.isEmpty(attrs)) {
                attrs.forEach(attr -> {
                    goodsAttrService.saveOrUpdateGoodsAttr(goodsId, attr.getAttr_id(), attr.getAttr_value());
                });
            }
        }, executor);
        try {
            CompletableFuture.allOf(GoodsInfoTask, GoodsPicsTask, GoodsAttrsTask).get();
        } catch (Exception e) {
            log.error("线程池任务异常：", e);
            throw new RuntimeException("更新商品信息失败");
        }
        return getOne(goodsId);
    }


    private GoodsListPageVO.Goods convertGoodsEntity2GoodsItem(GoodsEntity goodsEntity) {
        if (goodsEntity == null) {
            return null;
        }
        GoodsListPageVO.Goods vo = new GoodsListPageVO.Goods();
        vo.setGoods_id(goodsEntity.getGoodsId());
        vo.setGoods_name(goodsEntity.getGoodsName());
        vo.setGoods_number(goodsEntity.getGoodsNumber());
        vo.setGoods_price(goodsEntity.getGoodsPrice());
        vo.setGoods_weight(goodsEntity.getGoodsWeight());
        vo.setGoods_state(goodsEntity.getGoodsState());
        vo.setAdd_time(goodsEntity.getAddTime());
        vo.setUpd_time(goodsEntity.getUpdTime());
        vo.setHot_number(goodsEntity.getHotNumber());
        vo.setIs_promote(goodsEntity.getIsPromote() == 1 ? true : false);
        return vo;
    }

}