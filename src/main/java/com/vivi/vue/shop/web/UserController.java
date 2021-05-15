package com.vivi.vue.shop.web;

import com.alibaba.fastjson.JSONObject;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.service.ManagerService;
import com.vivi.vue.shop.utils.R;
import com.vivi.vue.shop.vo.PageQueryVO;
import com.vivi.vue.shop.vo.UserAddVO;
import com.vivi.vue.shop.vo.UserListPageVO;
import com.vivi.vue.shop.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 管理员表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@RestController
public class UserController {

    @Autowired
    private ManagerService managerService;

    /**
     * 列表
     */
    @GetMapping("/users")
    public R list(@Validated PageQueryVO pageQueryVO){
        UserListPageVO page = managerService.queryPage(pageQueryVO);
        return R.ok().setMsg("获取成功").setData(page);
    }

    /**
     * 单个
     */
    @GetMapping("/users/{id}")
    public R list(@PathVariable("id") Integer id){
        UserVO vo = managerService.getOne(id);
        return R.ok().setMsg("查询成功").setData(vo);
    }

    /**
     * 保存
     */
    @PostMapping("/users")
    public R save(@Validated UserAddVO userAddVO){
        UserVO vo = managerService.save(userAddVO);
        return R.create(BizCodeEnum.CREATED.getErrCode(), "用户添加成功")
                .setData(vo);
    }

    /**
     * 修改状态
     */
    @PutMapping("/users/{id}/state/{type}")
    public R updateState(@PathVariable("id") Integer id,
                         @PathVariable("type") boolean type) {
        UserVO vo = managerService.updateStatus(id, type);
        return R.ok().setMsg("设置状态成功").setData(vo);
    }

    /**
     * 修改手机号和邮箱
     */
    @PutMapping("/users/{id}")
    public R update(@PathVariable("id") Integer id,
                    @RequestParam(value = "mobile", defaultValue = "") String mobile,
                    @RequestParam(value = "email", defaultValue = "") String email){
        UserVO vo = managerService.updateInfo(id, mobile, email);
        return R.ok().setMsg("更新成功").setData(vo);
    }


    /**
     * 删除
     */
    @DeleteMapping("/users/{id}")
    public R delete(@PathVariable("id") Integer id){
		managerService.deleteOne(id);
        return R.ok().setMsg("删除成功");
    }

    /**
     * 分配角色
     *
     * 请求体里面设置要分配的角色id  {"rid": 1}
     */
    @PutMapping("/users/{id}/role")
    public R assignRole(@PathVariable("id") Integer id,
                        @RequestBody JSONObject jsonObject) {
        UserVO vo = managerService.assignRole(id, jsonObject);
        return R.ok().setMsg("设置角色成功").setData(vo);
    }
        

}
