package com.vivi.vue.shop.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangwei
 * 2021/2/9 20:44
 *
 *
 * 角色信息模型
 */
@Data
public class RoleWithRightVO {

    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 角色所具有的权限，树形结构
     */
    // @JsonIgnoreProperties(value = {"level", "pid"})
    private List<RightsTreeVO> children = new ArrayList<>();

}
