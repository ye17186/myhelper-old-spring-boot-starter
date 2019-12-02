package com.yclouds.myhelper.feign.fallback;

import com.yclouds.myhelper.web.error.code.BaseEnumError;
import com.yclouds.myhelper.web.response.ApiResp;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * AbstractFallback
 *
 * @author yemeng-lhq
 * @version 2019/11/28 17:52
 */
@Slf4j
@Setter
@Getter
public abstract class AbstractFeignFallback {

    @Value("${spring.application.name:unknown}")
    private String serviceId;

    /**
     * Feign发生fallback时错误日志打印
     *
     * @param cause 错误异常
     */
    protected void printLog(Throwable cause) {
        log.error("服务[{}] fallback.", serviceId, cause);
    }

    /**
     * Feign发生fallback时的处理
     *
     * @param method 发生fallback具体处理
     * @param <T> 响应体泛型
     * @return 具体响应
     */
    protected <T> ApiResp<T> fallback(String method) {

        return ApiResp.retFail(BaseEnumError.SERVICE_DOWN.getCode(),
            BaseEnumError.SERVICE_DOWN.getMsg().replace("${serviceId}", serviceId + "#" + method));
    }

    /**
     * Feign发生fallback时的处理
     *
     * @param <T> 响应体泛型
     * @return 具体响应
     */
    protected <T> ApiResp<T> fallback() {

        return ApiResp.retFail(BaseEnumError.SERVICE_DOWN.getCode(),
            BaseEnumError.SERVICE_DOWN.getMsg().replace("${serviceId}", serviceId));
    }
}
