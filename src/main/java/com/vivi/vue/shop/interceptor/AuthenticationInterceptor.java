package com.vivi.vue.shop.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.exception.BizException;
import com.vivi.vue.shop.utils.JwtUtils;
import com.vivi.vue.shop.vo.LoginRespVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangwei
 * 2021/2/8 22:58
 *
 * 拦截所有请求，校验令牌
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    public static ThreadLocal<LoginRespVO> threadLocal = new ThreadLocal();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 预检请求直接放行
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String authorize = request.getHeader("Authorization");
        // 授权字段空
        if (StringUtils.isEmpty(authorize)) {
            throw new BizException(BizCodeEnum.UNAUTHORIZED, "未授权，请先登录");
        }
        // 令牌有误
        if (!authorize.startsWith("Bearer")) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "无效令牌");
        }
        // 令牌校验失败会被异常处理机制处理
        String[] info = authorize.split(" ");
        DecodedJWT decodedJWT = JwtUtils.verifyToken(info[1]);
        // 从中拿出用户信息，保存到threadlocal
        String s = decodedJWT.getClaim(JwtUtils.CLAIM_USER_KEY).asString();
        LoginRespVO userInfo = JSON.parseObject(s, LoginRespVO.class);
        threadLocal.set(userInfo);
        return true;
    }
}
