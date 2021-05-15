package com.vivi.vue.shop.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivi.vue.shop.entity.ExpressEntity;
import com.vivi.vue.shop.service.ExpressService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.R;



/**
 * 快递表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@RestController
@RequestMapping("shop/express")
public class ExpressController {
    @Autowired
    private ExpressService expressService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = expressService.queryPage(params);

        return R.ok().setData(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{expressId}")
    public R info(@PathVariable("expressId") Integer expressId){
		ExpressEntity express = expressService.getById(expressId);

        return R.ok().setData(express);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ExpressEntity express){
		expressService.save(express);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ExpressEntity express){
		expressService.updateById(express);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] expressIds){
		expressService.removeByIds(Arrays.asList(expressIds));

        return R.ok();
    }

}
