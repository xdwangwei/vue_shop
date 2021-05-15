package com.vivi.vue.shop.web;

import com.alibaba.fastjson.JSONObject;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.service.RoleService;
import com.vivi.vue.shop.utils.R;
import com.vivi.vue.shop.vo.RoleVO;
import com.vivi.vue.shop.vo.RoleWithRightVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 列表
     */
    @GetMapping("/roles")
    public R list() {
        List<RoleWithRightVO> roles = roleService.listWithRights();
        return R.ok().setMsg("获取角色及权限成功").setData(roles);
    }

    /**
     * 信息
     */
    @GetMapping("/roles/{roleId}")
    public R info(@PathVariable("roleId") Integer roleId) {
        RoleVO role = roleService.getOne(roleId);
        return R.ok().setMsg("获取成功").setData(role);
    }

    /**
     * 修改
     */
    @PutMapping("/roles/{roleId}")
    public R update(@PathVariable("roleId") Integer roleId,
                    @Validated RoleVO roleVO) {
        RoleVO role = roleService.update(roleId, roleVO);
        return R.ok().setMsg("修改成功").setData(role);
    }

    /**
     * 保存
     */
    @PostMapping("/roles")
    public R save(@Validated RoleVO roleVO) {
        RoleVO vo = roleService.add(roleVO);
        return R.create(BizCodeEnum.CREATED.getErrCode(), BizCodeEnum.CREATED.getErrMsg())
                .setData(vo);
    }

    /**
     * 为角色赋予权限
     * 请求体 {"rids":"1,2,3"}
     */
    @PostMapping("/roles/{roleId}/rights")
    public R assignRight(@PathVariable("roleId") Integer roleId, @RequestBody JSONObject object) {
        roleService.assignRights(roleId, object);
        return R.ok().setMsg("更新角色权限成功");
    }


    /**
     * 删除
     */
    @DeleteMapping("/roles/{roleId}")
    public R delete(@PathVariable("roleId") Integer roleId) {
        roleService.removeById(roleId);
        return R.ok().setMsg("删除成功");
    }

    /**
     * 删除角色指定权限
     */
    @DeleteMapping("/roles/{roleId}/rights/{rightId}")
    public R removeRight(@PathVariable("roleId") Integer roleId,
                         @PathVariable("rightId") Integer rightId) {
        RoleWithRightVO vo = roleService.removeRight(roleId, rightId);
        return R.ok().setMsg("取消权限成功").setData(vo);
    }

}
