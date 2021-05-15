package com.vivi.vue.shop.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.exception.BizException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author wangwei
 * 2021/2/8 22:16
 *
 * header
 *
 * payload
 *
 *  iss (issuer)：签发人
 *  exp (expiration time)：过期时间
 *  sub (subject)：主题
 *  aud (audience)：受众
 *  nbf (Not Before)：生效时间
 *  iat (Issued At)：签发时间
 *  jti (JWT ID)：编号
 *
 * sign
 */
@Slf4j
public class JwtUtils {

    // 秘钥
    public static final String SECRET_KEY = "f3g7h2dn@w2esdRhf9wj#Fh28d$";
    // 令牌颁发者
    public static final String ISSUER = "VUE_SHOP";
    // 用户信息key
    public static final String CLAIM_USER_KEY = "user";
    // 有效时间，30min，为方便，设置为10h
    public static final int TIMEOUT = 30 * 60 * 1000 * 20;
    // 加密算法
    public static final Algorithm SIGN_ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    /**
     * 创建token
     * @param user
     * @return
     */
    public static String createToken(Object user) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withExpiresAt(new Date(System.currentTimeMillis() + TIMEOUT))
                    .withClaim(CLAIM_USER_KEY, JSON.toJSONString(user))
                    .sign(SIGN_ALGORITHM);
        } catch (Exception e) {
            log.error("创建jwt token失败: {}", e);
            throw new BizException(BizCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 校验令牌
     * @param token
     * @return
     */
    public static DecodedJWT verifyToken(String token) {

        try {
            JWTVerifier verifier = JWT.require(SIGN_ALGORITHM)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (TokenExpiredException e) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "令牌已过期");
        } catch (SignatureVerificationException e) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "无效令牌");
        }
    }

    // public static void main(String[] args) {
    //     OrderEntity or = new OrderEntity();
    //     or.setOrderId(10001);
    //     or.setTradeNo("0000000888888");
    //     String token = createToken(or);
    //     System.out.println("token: " + token);
    //     DecodedJWT decodedJWT = verifyToken(token);
    //     String s = decodedJWT.getClaim(CLAIM_USER_KEY).asString();
    //     OrderEntity orderEntity = JSON.parseObject(s, OrderEntity.class);
    //     System.out.println(orderEntity);
    // }
}
