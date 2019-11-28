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

    SYSTEM_EXCEPTION(101, "系统异常，请联系管理员"),
    SYSTEM_REQUEST_URL_NOT_FOUND(102, "请求地址不存在"),
    SYSTEM_REQUEST_METHOD_NOT_SUPPORTED(103, "请求方法不支持"),
    SYSTEM_ARGUMENT_NOT_VALID(104, "请求参数不合法"),
    SYSTEM_TOKEN_INVALID_01(105, "非法请求"),
    SYSTEM_TOKEN_INVALID_02(106, "非法请求"),
    SYSTEM_TOKEN_INVALID_03(107, "非法请求"),
    SYSTEM_TOKEN_INVALID_04(108, "非法请求"),
    SYSTEM_TOKEN_INVALID_05(109, "非法请求"),
    SYSTEM_ASYNC_RESULT_GET_EX(110, "获取异步结果异常"),
    SYSTEM_ASYNC_RESULT_GET_TIMEOUT(111, "获取异步结果超时"),


    SYSTEM_NO_LOGIN(201, "用户未登录"),
    SERVICE_ERROR(202, "服务暂不可用，请稍后重试"),
    SERVICE_DOWN(203, "服务[${serviceId}]暂不可用，请稍后重试");

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
