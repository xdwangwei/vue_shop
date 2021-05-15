package com.vivi.vue.shop.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wangwei
 * 2021/2/9 10:11
 */
@Data
public class LoginVO {

    @NotNull(message = "username字段必填")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotNull(message = "password字段必填")
    @NotBlank(message = "密码不能为空")
    private String password;
}
