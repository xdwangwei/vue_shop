package com.vivi.vue.shop.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wangwei
 * 2021/2/9 12:36
 *
 * 添加用户请求参数封装
 */
@Data
public class UserAddVO {

    @NotNull(message = "username字段必填")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotNull(message = "password字段必填")
    @NotBlank(message = "密码不能为空")
    private String password;

    private String email;

    private String mobile;
}
