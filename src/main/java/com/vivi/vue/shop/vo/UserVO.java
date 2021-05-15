package com.vivi.vue.shop.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author wangwei
 * 2021/2/9 12:40
 */
@Data
public class UserVO {

    /**
     * 用户id
     */
    Integer id;

    /**
     * 角色id
     */
    Integer rid;

    /**
     * 角色名称
     */
    String roleName;

    /**
     * 用户名
     */
    String username;

    /**
     * 创建时间
     */
    Date createTime;

    /**
     * 电话
     */
    String mobile;

    /**
     * 邮箱
     */
    String email;

    /**
     * 启动状态
     */
    boolean mg_state;
}
