package com.vivi.vue.shop.vo;

import lombok.Data;

/**
 * @author wangwei
 * 2021/2/9 17:14
 *
 * 列表形式权限
 */
@Data
public class RightsListVO {

    /**
     *  "id": 101,
     *             "authName": "商品管理",
     *             "level": "0",
     *             "pid": 0,
     *             "path": null
     */
    // 权限id
    private Integer id;
    // 权限名字
    private String authName;
    // 权限等级
    private Integer level;
    // 父级权限id
    private String pid;
    // 权限层级路径
    private String path;
}
