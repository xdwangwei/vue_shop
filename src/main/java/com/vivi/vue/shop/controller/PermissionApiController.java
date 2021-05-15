package com.vivi.vue.shop.controller;

import com.vivi.vue.shop.entity.PermissionApiEntity;
import com.vivi.vue.shop.service.PermissionApiService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@RestController
@RequestMapping("shop/permissionapi")
public class PermissionApiController {
    @Autowired
    private PermissionApiService permissionApiService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = permissionApiService.queryPage(params);

        return R.ok().setData(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		PermissionApiEntity permissionApi = permissionApiService.getById(id);

        return R.ok().setData(permissionApi);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PermissionApiEntity permissionApi){
		permissionApiService.save(permissionApi);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PermissionApiEntity permissionApi){
		permissionApiService.updateById(permissionApi);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		permissionApiService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
