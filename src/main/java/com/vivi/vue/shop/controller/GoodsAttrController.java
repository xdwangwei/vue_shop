package com.vivi.vue.shop.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivi.vue.shop.entity.GoodsAttrEntity;
import com.vivi.vue.shop.service.GoodsAttrService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.R;



/**
 * 商品-属性关联表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@RestController
@RequestMapping("shop/goodsattr")
public class GoodsAttrController {
    @Autowired
    private GoodsAttrService goodsAttrService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsAttrService.queryPage(params);

        return R.ok().setData(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		GoodsAttrEntity goodsAttr = goodsAttrService.getById(id);

        return R.ok().setData(goodsAttr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody GoodsAttrEntity goodsAttr){
		goodsAttrService.save(goodsAttr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody GoodsAttrEntity goodsAttr){
		goodsAttrService.updateById(goodsAttr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		goodsAttrService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
