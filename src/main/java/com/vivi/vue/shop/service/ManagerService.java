package com.vivi.vue.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.entity.ManagerEntity;
import com.vivi.vue.shop.vo.PageQueryVO;
import com.vivi.vue.shop.vo.UserAddVO;
import com.vivi.vue.shop.vo.UserListPageVO;
import com.vivi.vue.shop.vo.UserVO;

/**
 * 管理员表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface ManagerService extends IService<ManagerEntity> {

    /**
     * 分页查询
     * @param pageQueryVO
     * @return
     */
    UserListPageVO queryPage(PageQueryVO pageQueryVO);

    /**
     * 添加用户
     * @param userAddVO
     * @return
     */
    UserVO save(UserAddVO userAddVO);

    /**
     * 更新用户状态
     * @param uid
     * @param type
     * @return
     */
    UserVO updateStatus(Integer uid, boolean type);

    /**
     * 根据id查询用户
     * @param uid
     * @return
     */
    UserVO getOne(Integer uid);

    /**
     * 更新指定用户的手机号和邮箱
     * @param uid
     * @param mobile
     * @param email
     * @return
     */
    UserVO updateInfo(Integer uid, String mobile, String email);

    /**
     * 删除指定用户
     * @param uid
     */
    void deleteOne(Integer uid);

    /**
     * 设置用户角色
     * @param uid
     * @param jsonObject
     */
    UserVO assignRole(Integer uid, JSONObject jsonObject);
}

