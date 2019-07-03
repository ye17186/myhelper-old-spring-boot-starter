package com.yclouds.myhelper.web.error.code;

import lombok.Getter;

/**
 * 基础公用错误码(错误代码统一规划，以10开头)
 *
 * @author ye17186
 * @version 2019/2/18 15:49
 */
@Getter
public enum BaseEnumError implements IEnumError {

    SYSTEM_EXCEPTION(10001, "系统异常，请联系管理员"),
    SYSTEM_REQUEST_URL_NOT_FOUND(10002, "请求地址不存在"),
    SYSTEM_REQUEST_METHOD_NOT_SUPPORTED(10003, "请求方法不支持"),
    SYSTEM_ARGUMENT_NOT_VALID1(10004, "业务参数不合法"),
    SYSTEM_ARGUMENT_NOT_VALID2(10005, "接口参数不合法"),
    SYSTEM_TOKEN_INVALID_01(10011, "非法请求"),
    SYSTEM_TOKEN_INVALID_02(10012, "非法请求"),
    SYSTEM_TOKEN_INVALID_03(10013, "非法请求"),
    SYSTEM_TOKEN_INVALID_04(10014, "非法请求"),
    SYSTEM_TOKEN_INVALID_05(10015, "非法请求"),


    SYSTEM_NO_LOGIN(10104, "用户未登录"),
    SERVICE_ERROR(10101, "服务暂不可用，请稍后重试"),
    SERVICE_DOWN(10102, "服务[${serviceId}]暂不可用，请稍后重试");

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String msg;

    BaseEnumError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
