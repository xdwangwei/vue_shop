package com.vivi.vue.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.dao.CategoryDao;
import com.vivi.vue.shop.entity.CategoryEntity;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.exception.BizException;
import com.vivi.vue.shop.service.CategoryService;
import com.vivi.vue.shop.vo.CategoryListPageVO;
import com.vivi.vue.shop.vo.CategoryPageQueryVO;
import com.vivi.vue.shop.vo.CategoryTreeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {


    /**
     * 分页、按条件查询商品分类
     * 如果未传分页参数，直接返回列表
     * 如果传了分页参数，则返回分页数据模型
     * @param pageQueryVO
     * @return
     */
    @Override
    public Object listOrPage(CategoryPageQueryVO pageQueryVO) {
        // 1. 判断查询类型
        Integer type = pageQueryVO.getType();
        // 不合法参数统一当做查所有分类处理
        if (type == null || (type != 1 && type != 2)) {
            type = 3;
        }
        // 2. 查询出前type层级的分类，并转成需要的实体类对象
        List<CategoryTreeVO> treeVOList = this.list(new QueryWrapper<CategoryEntity>().le("cat_level", type)).stream()
                .map(categoryEntity -> convertCategoryEntity2CategoryTreeVo(categoryEntity))
                .collect(Collectors.toList());
        // 3. 过滤出所有一级分类，并为其组装子级分类
        List<CategoryTreeVO> collect = treeVOList.stream().filter(treeVO -> treeVO.getCat_pid() == 0)
                .map(treeVO -> getChildren(treeVO, treeVOList))
                .collect(Collectors.toList());
        // 4. 构建分页返回对象
        Integer pagenum = pageQueryVO.getPagenum();
        Integer pagesize = pageQueryVO.getPagesize();
        // 如果没有查询参数或者参数不全，直接返回列表
        if (pagenum == null || pagesize == null) {
            return collect;
        }
        // 否则返回分页结果
        CategoryListPageVO pageVO = new CategoryListPageVO();
        pageVO.setTotal(collect.size());
        if (pagenum < 1) {
            pagenum = 1;
        }
        if (pagesize < 1) {
            pagesize = 1;
        }
        pageVO.setPagenum(pagenum);
        pageVO.setPagesize(pagesize);
        int start = (pagenum - 1) * pagesize;
        int end = Math.min(start + pagesize, collect.size());
        // 5. 按照分页查询取出部分作为结果返回
        pageVO.setResult(collect.subList(start, end));
        return pageVO;
    }

    @Override
    public CategoryTreeVO add(Integer catPid, String catName, Integer catLevel) {
        if (StringUtils.isBlank(catName)) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "请求参数cat_name不能为空");
        }
        if (catLevel < 0 || catLevel > 2) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "请求参数cat_level只能取0或1或2");
        }
        // 二级三级分类。父分类id必须存在
        if (catLevel != 0) {
            CategoryEntity entity = this.getById(catPid);
            if (entity == null) {
                throw new BizException(BizCodeEnum.BAD_REQUEST, "请求参数cat_pid指定的分类不存在");
            }
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCatName(catName);
        categoryEntity.setCatLevel(catLevel);
        categoryEntity.setCatPid(catPid);
        categoryEntity.setCatDeleted(0);
        this.save(categoryEntity);
        return convertCategoryEntity2CategoryTreeVo(categoryEntity);
    }

    @Override
    public CategoryTreeVO getOne(Integer catId) {
        CategoryEntity entity = this.getById(catId);
        if (entity == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "所选分类不存在");
        }
        return convertCategoryEntity2CategoryTreeVo(entity);
    }

    @Override
    public CategoryTreeVO updateCategory(Integer catId, JSONObject jsonObject) {
        String catName = (String) jsonObject.get("cat_name");
        if (StringUtils.isEmpty(catName)) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "请求体参数'cat_name'不能为空");
        }
        CategoryEntity entity = this.getById(catId);
        if (entity == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "所选分类不存在");
        }
        CategoryEntity entity1 = new CategoryEntity();
        entity1.setCatId(catId);
        entity1.setCatName(catName);
        this.updateById(entity1);
        return convertCategoryEntity2CategoryTreeVo(this.getById(catId));
    }

    /**
     * 传入一个一级分类和全部分类，递归找到这个分类的全部孩子，设置到他相应的属性，再把它返回
     * @return
     */
    private CategoryTreeVO getChildren(CategoryTreeVO treeVO, List<CategoryTreeVO> all) {
        treeVO.setChildren(all.stream()
                // 找到他的下一级
                .filter(category -> category.getCat_pid().equals(treeVO.getCat_id()))
                // 每个下一级，通过这个函数继续寻找自己的下一级
                .map(category -> getChildren(category, all))
                .collect(Collectors.toList()));
        return treeVO;
    }

    private CategoryTreeVO convertCategoryEntity2CategoryTreeVo(CategoryEntity categoryEntity) {
        CategoryTreeVO treeVo = new CategoryTreeVO();
        treeVo.setCat_id(categoryEntity.getCatId());
        treeVo.setCat_name(categoryEntity.getCatName());
        treeVo.setCat_pid(categoryEntity.getCatPid());
        treeVo.setCat_level(categoryEntity.getCatLevel());
        treeVo.setCat_deleted(categoryEntity.getCatDeleted() == 1 ? true : false);
        return treeVo;
    }
}