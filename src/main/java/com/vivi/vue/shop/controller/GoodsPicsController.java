package com.vivi.vue.shop.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivi.vue.shop.entity.GoodsPicsEntity;
import com.vivi.vue.shop.service.GoodsPicsService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.R;



/**
 * 商品-相册关联表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@RestController
@RequestMapping("shop/goodspics")
public class GoodsPicsController {
    @Autowired
    private GoodsPicsService goodsPicsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsPicsService.queryPage(params);

        return R.ok().setData(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{picsId}")
    public R info(@PathVariable("picsId") Integer picsId){
		GoodsPicsEntity goodsPics = goodsPicsService.getById(picsId);

        return R.ok().setData(goodsPics);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody GoodsPicsEntity goodsPics){
		goodsPicsService.save(goodsPics);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody GoodsPicsEntity goodsPics){
		goodsPicsService.updateById(goodsPics);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] picsIds){
		goodsPicsService.removeByIds(Arrays.asList(picsIds));

        return R.ok();
    }

}
