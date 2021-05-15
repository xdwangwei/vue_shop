package com.vivi.vue.shop.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangwei
 * 2021/2/9 19:51
 *
 * 左侧菜单数据模型
 */
@Data
public class MenuTreeVO {

    // 权限id
    private Integer id;
    // 权限名字
    private String authName;

    // 权限访问路径
    private String path;

    /**
     * 父菜单id
     * 文档中返回数据无此字段
     * 为了方便处理业务设置
     * 返回时忽略
     */
    @JsonIgnore
    private String pid;

    // 子权限
    private List<MenuTreeVO> children = new ArrayList<>();
}
