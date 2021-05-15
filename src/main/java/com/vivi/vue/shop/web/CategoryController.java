package com.vivi.vue.shop.web;

import com.alibaba.fastjson.JSONObject;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.service.CategoryService;
import com.vivi.vue.shop.utils.R;
import com.vivi.vue.shop.vo.CategoryPageQueryVO;
import com.vivi.vue.shop.vo.CategoryTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表 / 分页
     */
    @GetMapping("/categories")
    public R list(CategoryPageQueryVO pageQueryVO){
        Object res = categoryService.listOrPage(pageQueryVO);
        return R.ok().setMsg("获取商品分类成功").setData(res);
    }

    /**
     * 信息
     */
    @GetMapping("/categories/{catId}")
    public R info(@PathVariable("catId") Integer catId){
        CategoryTreeVO category = categoryService.getOne(catId);

        return R.ok().setMsg("查询分类信息成功").setData(category);
    }

    /**
     * 保存
     */
    @PostMapping("/categories")
    public R save(@RequestParam("cat_pid") Integer catPid,
                  @RequestParam("cat_name") String catName,
                  @RequestParam("cat_level") Integer catLevel){
        CategoryTreeVO category = categoryService.add(catPid, catName, catLevel);

        return R.create(BizCodeEnum.CREATED.getErrCode(), BizCodeEnum.CREATED.getErrMsg()).setData(category);
    }

    /**
     * 删除
     */
    @DeleteMapping("/categories/{catId}")
    public R delete(@PathVariable("catId") Integer catId){
        categoryService.removeById(catId);

        return R.ok().setMsg("删除成功");
    }


    /**
     * 修改
     */
    @PutMapping("/categories/{catId}")
    public R update(@PathVariable("catId") Integer catId,
                    @RequestBody JSONObject jsonObject){
		CategoryTreeVO category = categoryService.updateCategory(catId, jsonObject);

        return R.ok().setMsg("更新成功").setData(category);
    }


}
