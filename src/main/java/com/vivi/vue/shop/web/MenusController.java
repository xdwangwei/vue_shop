package com.vivi.vue.shop.web;

import com.vivi.vue.shop.service.PermissionService;
import com.vivi.vue.shop.utils.R;
import com.vivi.vue.shop.vo.MenuTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangwei
 * 2021/2/9 19:47
 *
 * 左侧菜单列表，其实就是一二级权限
 */
@RestController
public class MenusController {

    @Autowired
    PermissionService permissionService;

    @GetMapping("/menus")
    public R menus() {
        List<MenuTreeVO> list = permissionService.level2Permission();
        return R.ok().setMsg("获取菜单列表成功").setData(list);
    }
}
