package com.vivi.vue.shop.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wangwei
 * 2021/2/9 11:35
 *
 * 分页查询用户列表返回数据模型
 */
@Data
public class UserListPageVO {

    // 总记录数
    private Integer totalpage;

    // 当前页码
    private Integer pagenum;

    // 用户列表
    private List<UserVO> users;

}
