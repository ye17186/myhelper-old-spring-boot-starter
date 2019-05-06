package com.yclouds.myhelper.plugins.advice;

import com.yclouds.myhelper.autoconfigure.properties.AdviceProperties;
import com.yclouds.myhelper.web.error.code.BaseError;
import com.yclouds.myhelper.web.response.ApiResp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ye17186
 * @version 2019/5/6 15:16
 */
@SuppressWarnings("unused")
@Slf4j
@Configuration
@EnableConfigurationProperties(AdviceProperties.class)
@ConditionalOnProperty(prefix = "myhelper.plugins.advice", name = "enabled", havingValue = "true")
@ControllerAdvice
public class ControllerAdviceConfiguration {
    /**
     * 参数校验不通过异常
     *
     * @param request Http请求
     * @param ex 异常对象
     * @see javax.validation.Valid
     * @see org.springframework.validation.annotation.Validated
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    protected ApiResp handleException(HttpServletRequest request,
        MethodArgumentNotValidException ex) {

        log.warn("[Method Arg Not Valid] url: {}", request.getRequestURL().toString());

        List<ObjectError> list = ex.getBindingResult().getAllErrors();
        StringBuffer detail = new StringBuffer();
        if (!ObjectUtils.isEmpty(list)) {
            list.forEach(item -> detail.append(item.getDefaultMessage()).append(";"));
        }

        return ApiResp.retFail(BaseError.SYSTEM_ARGUMENT_NOT_VALID, detail.toString());
    }

    /**
     * 请求方法（GET、POST等）不支持异常处理
     *
     * @param request Http请求
     * @param ex 异常对象
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    protected ApiResp handleException(HttpServletRequest request,
        HttpRequestMethodNotSupportedException ex) {

        String method = request.getMethod();
        log.warn("[Method Not Supported] url: {}，method: {}", request.getRequestURL(), method);

        return ApiResp.retFail(BaseError.SYSTEM_REQUEST_METHOD_NOT_SUPPORTED, method);
    }

    /**
     * 最后兜底的异常处理
     * <br>
     * 由请求接口不存在产生的404异常，不会走到这里，所有另外定义了YErrorHandler来处理这类异常
     *
     * @param request Http请求
     * @param ex 异常对象
     * @see com.yclouds.myhelper.web.error.handler.YErrorHandler
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    protected ApiResp handleException(HttpServletRequest request, Exception ex) {

        log.error("[System Exception] url: {}", request.getRequestURL(), ex);
        return ApiResp.retFail(BaseError.SYSTEM_EXCEPTION);
    }
}
