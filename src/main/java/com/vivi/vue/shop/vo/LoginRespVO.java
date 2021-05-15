package com.vivi.vue.shop.vo;

import lombok.Data;

/**
 * @author wangwei
 * 2021/2/8 23:19
 *
 * 登录成功后的用户信息
 */
@Data
public class LoginRespVO {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 令牌
     */
    private String token;
}
