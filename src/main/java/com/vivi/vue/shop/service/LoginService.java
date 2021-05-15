package com.vivi.vue.shop.service;

import com.vivi.vue.shop.vo.LoginVO;
import com.vivi.vue.shop.vo.LoginRespVO;

/**
 * @author wangwei
 * 2021/2/8 22:55
 */
public interface LoginService {

    /**
     * 处理登录请求
     */
    LoginRespVO login(LoginVO loginVO);
}
