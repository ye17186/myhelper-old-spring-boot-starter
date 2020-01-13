package com.yclouds.myhelper.feign.fallback;

import com.yclouds.myhelper.web.error.code.BaseEnumError;
import com.yclouds.myhelper.web.response.ApiResp;
import feign.hystrix.FallbackFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * AbstractFeignFallback
 *
 * @author ye17186-lhq
 * @version 2019/11/28 17:52
 */
@Slf4j
@Setter
@Getter
public abstract class AbstractFeignFallback<Client> implements FallbackFactory<Client> {

    @Value("${spring.application.name:unknown}")
    private String serviceId;

    @Override
    public Client create(Throwable cause) {
        printLog(cause);
        return createClient();
    }


    /**
     * 构建一个服务降级FeignClient
     *
     * @return 降级FeignClient
     */
    public abstract Client createClient();

    /**
     * Feign发生fallback时错误日志打印
     *
     * @param cause 错误异常
     */
    private void printLog(Throwable cause) {
        log.error("服务[{}] fallback due to", serviceId, cause);
    }

    /**
     * Feign发生fallback时的处理
     *
     * @param method 发生fallback具体处理
     * @param <T> 响应体泛型
     * @return 具体响应
     */
    protected <T> ApiResp<T> response(String method) {

        return ApiResp.retFail(BaseEnumError.SERVICE_DOWN.getCode(),
            BaseEnumError.SERVICE_DOWN.getMsg().replace("${serviceId}", serviceId + "#" + method));
    }

    /**
     * Feign发生fallback时的处理
     *
     * @param <T> 响应体泛型
     * @return 具体响应
     */
    protected <T> ApiResp<T> response() {

        return ApiResp.retFail(BaseEnumError.SERVICE_DOWN.getCode(),
            BaseEnumError.SERVICE_DOWN.getMsg().replace("${serviceId}", serviceId));
    }
}
