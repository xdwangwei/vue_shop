package com.vivi.vue.shop.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author wangwei
 * 2021/2/9 17:15
 *
 * 树形结构的权限信息
 */
@Data
public class RightsTreeVO {

    /**
     *         id: 101,
     *         authName: '商品管理',
     *         path: null,
     *         pid: 0,
     *         children: [
     *           {
     *             id: 104,
     *             authName: '商品列表',
     *             path: null,
     *             pid: 101,
     *             children: [
     *               {
     *                 id: 105,
     *                 authName: '添加商品',
     *                 path: null,
     *                 pid: '104,101'
     *               }
     *             ]
     *           }
     *         ]
     */
    // 权限id
    private Integer id;
    // 权限名字
    private String authName;
    // 权限等级,0 是1级，1是2级，2是3级
    @JsonIgnore
    private Integer level;
    /**
     * 父级权限id路径，
     * 一级的pid是0，
     * 二级的pid是 一级的id，比如 '101'
     * 三级的pid是二级的id，二级的pid，比如 '104,101'
     */
    private String pid;

    // 权限访问路径
    private String path;

    // 子权限
    /**
     * 	对应的子分类，表中不存在的字段
     * 	如果为空(三级菜单没有孩子)，则返回给前端时序列化时不包含此字段
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<RightsTreeVO> children;
}
