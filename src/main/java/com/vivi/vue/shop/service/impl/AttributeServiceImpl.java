package com.vivi.vue.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.dao.AttributeDao;
import com.vivi.vue.shop.entity.AttributeEntity;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.exception.BizException;
import com.vivi.vue.shop.service.AttributeService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;
import com.vivi.vue.shop.vo.AttributeAddVO;
import com.vivi.vue.shop.vo.CategoryAttributeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attributeService")
public class AttributeServiceImpl extends ServiceImpl<AttributeDao, AttributeEntity> implements AttributeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttributeEntity> page = this.page(
                new Query<AttributeEntity>().getPage(params),
                new QueryWrapper<AttributeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryAttributeVO> listByCatIdAndSelType(Integer catId, String sel) {
        if (!sel.equals("only") && !sel.equals("many")) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "请求参数sel可选值为only或many");
        }

        return this.list(new QueryWrapper<AttributeEntity>().eq("cat_id", catId).eq("attr_sel", sel))
                .stream()
                .map(attributeEntity -> convertAttributeEntity2CategoryAttributeVO(attributeEntity))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryAttributeVO add(Integer catId, AttributeAddVO addVO) {
        AttributeEntity attributeEntity = new AttributeEntity();
        attributeEntity.setCatId(catId);
        attributeEntity.setAttrName(addVO.getAttr_name());
        attributeEntity.setAttrSel(addVO.getAttr_sel());
        attributeEntity.setAttrVals(addVO.getAttr_vals());
        this.save(attributeEntity);
        return convertAttributeEntity2CategoryAttributeVO(this.getById(attributeEntity.getAttrId()));
    }

    @Override
    public CategoryAttributeVO getOne(Integer attrId) {

        AttributeEntity byId = this.getById(attrId);
        if (byId == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "参数attrId所指分类数据不存在");
        }
        return convertAttributeEntity2CategoryAttributeVO(byId);
    }

    @Override
    public CategoryAttributeVO update(Integer attrId, AttributeAddVO attrVO) {
        AttributeEntity byId = this.getById(attrId);
        if (byId == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "参数attrId所指分类数据不存在");
        }
        AttributeEntity entity = new AttributeEntity();
        entity.setAttrId(attrId);
        entity.setAttrName(attrVO.getAttr_name());
        entity.setAttrSel(attrVO.getAttr_sel());
        if (StringUtils.isNotBlank(attrVO.getAttr_vals())) {
            entity.setAttrVals(attrVO.getAttr_vals());
        }
        this.updateById(entity);
        return convertAttributeEntity2CategoryAttributeVO(this.getById(attrId));
    }

    private CategoryAttributeVO convertAttributeEntity2CategoryAttributeVO(AttributeEntity attributeEntity) {

        if (attributeEntity == null) {
            return null;
        }
        CategoryAttributeVO categoryAttributeVO = new CategoryAttributeVO();
        categoryAttributeVO.setCat_id(attributeEntity.getCatId());
        categoryAttributeVO.setAttr_id(attributeEntity.getAttrId());
        categoryAttributeVO.setAttr_name(attributeEntity.getAttrName());
        categoryAttributeVO.setAttr_sel(attributeEntity.getAttrSel());
        categoryAttributeVO.setAttr_write(attributeEntity.getAttrWrite());
        categoryAttributeVO.setAttr_vals(attributeEntity.getAttrVals());
        return categoryAttributeVO;
    }

}