package com.vivi.vue.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.dao.GoodsAttrDao;
import com.vivi.vue.shop.entity.GoodsAttrEntity;
import com.vivi.vue.shop.service.GoodsAttrService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("goodsAttrService")
public class GoodsAttrServiceImpl extends ServiceImpl<GoodsAttrDao, GoodsAttrEntity> implements GoodsAttrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsAttrEntity> page = this.page(
                new Query<GoodsAttrEntity>().getPage(params),
                new QueryWrapper<GoodsAttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<GoodsAttrEntity> listByGoodsId(Integer goodsId) {

        return this.list(new QueryWrapper<GoodsAttrEntity>().eq("goods_id", goodsId));
    }

    @Override
    public boolean removeByGoodsId(Integer goodsId) {

        return this.remove(new QueryWrapper<GoodsAttrEntity>().eq("goods_id", goodsId));
    }

    @Transactional
    @Override
    public int saveOrUpdateGoodsAttr(Integer goodsId, Integer attrId, String attrValue) {
        GoodsAttrEntity entity = this.getOne(new QueryWrapper<GoodsAttrEntity>().eq("goods_id", goodsId).eq("attr_id", attrId));
        if (entity != null) {
            // 已有就修改
            GoodsAttrEntity update = new GoodsAttrEntity();
            update.setId(entity.getId());
            update.setAttrValue(attrValue);
            this.updateById(update);
        } else {
            //    没有就新增
            GoodsAttrEntity attrEntity = new GoodsAttrEntity();
            attrEntity.setAttrId(attrId);
            attrEntity.setGoodsId(goodsId);
            attrEntity.setAttrValue(attrValue);
            this.save(attrEntity);
        }
        return 1;
    }

}