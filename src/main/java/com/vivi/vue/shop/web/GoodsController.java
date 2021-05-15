package com.vivi.vue.shop.web;

import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.service.GoodsService;
import com.vivi.vue.shop.utils.R;
import com.vivi.vue.shop.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 商品表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 列表
     */
    @GetMapping("/goods")
    public R list(@Validated PageQueryVO pageQueryVO){
        GoodsListPageVO page = goodsService.queryPage(pageQueryVO);

        return R.ok().setMsg("获取成功").setData(page);
    }

    /**
     * 保存
     */
    @PostMapping("/goods")
    public R save(@Validated @RequestBody GoodsAddVO goods){
        GoodsVO goodsVO = goodsService.add(goods);
        return R.create(BizCodeEnum.CREATED.getErrCode(), BizCodeEnum.CREATED.getErrMsg()).setData(goodsVO);
    }


    /**
     * 信息
     */
    @GetMapping("/goods/{goodsId}")
    public R info(@PathVariable("goodsId") Integer goodsId){
		GoodsVO goods = goodsService.getOne(goodsId);

        return R.ok().setMsg("获取成功").setData(goods);
    }

    /**
     * 修改
     */
    @PutMapping("/goods/{goodsId}")
    public R update(@PathVariable("goodsId") Integer goodsId,
                    @Validated @RequestBody GoodsUpdateVO updateVO){
		GoodsVO goodsVO = goodsService.updateById(goodsId, updateVO);

        return R.ok().setMsg("更新成功").setData(goodsVO);
    }

    /**
     * 删除
     */
    @DeleteMapping("/goods/{goodsId}")
    public R delete(@PathVariable("goodsId") Integer goodsId){
		goodsService.removeCascadeById(goodsId);
        return R.ok().setMsg("删除成功");
    }

}
