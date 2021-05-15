package com.vivi.vue.shop.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wangwei
 * 2021/2/9 21:
 *
 * 用于页面展示的角色信息
 */
@Data
public class RoleVO {

    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 角色名称
     */
    @NotBlank(message = "'roleName' 字段不能为空")
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;
}
