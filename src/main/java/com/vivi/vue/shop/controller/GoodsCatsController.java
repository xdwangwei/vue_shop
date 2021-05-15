package com.vivi.vue.shop.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivi.vue.shop.entity.GoodsCatsEntity;
import com.vivi.vue.shop.service.GoodsCatsService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.R;



/**
 * 
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@RestController
@RequestMapping("shop/goodscats")
public class GoodsCatsController {
    @Autowired
    private GoodsCatsService goodsCatsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsCatsService.queryPage(params);

        return R.ok().setData(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Integer catId){
		GoodsCatsEntity goodsCats = goodsCatsService.getById(catId);

        return R.ok().setData(goodsCats);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody GoodsCatsEntity goodsCats){
		goodsCatsService.save(goodsCats);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody GoodsCatsEntity goodsCats){
		goodsCatsService.updateById(goodsCats);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] catIds){
		goodsCatsService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
