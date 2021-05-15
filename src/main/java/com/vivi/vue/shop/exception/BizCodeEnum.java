package com.vivi.vue.shop.exception;

/**
 * @author wangwei
 * 2021/2/8 21:23
 */
public enum BizCodeEnum implements CommonError{

    OK("200", "请求成功"),
    CREATED("201", "创建成功"),
    DELETED("204", "删除成功"),
    BAD_REQUEST("400", "请求的地址不存在或者包含不支持的参数"),
    UNAUTHORIZED("401", "未授权"),
    FORBIDDEN("403", "禁止访问"),
    NOT_FOUND("404", "请求资源不存在"),
    UNPROCESSABLE_ENTITY("422", "[POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误"),
    INTERNAL_SERVER_ERROR("500", "内部错误");

    private String code;

    private String msg;

    BizCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getErrMsg() {
        return this.msg;
    }

    @Override
    public String getErrCode() {
        return this.code;
    }
}
