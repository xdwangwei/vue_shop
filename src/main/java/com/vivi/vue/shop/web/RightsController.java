package com.vivi.vue.shop.web;

import com.vivi.vue.shop.service.PermissionService;
import com.vivi.vue.shop.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangwei
 * 2021/2/9 17:11
 *
 * 权限
 */
@RestController
public class RightsController {

    @Autowired
    PermissionService permissionService;

    @GetMapping("/rights/{type}")
    public R rights(@PathVariable("type") String type) {
        List list = permissionService.listByType(type);
        return R.ok().setMsg("获取权限列表成功").setData(list);
    }
}
