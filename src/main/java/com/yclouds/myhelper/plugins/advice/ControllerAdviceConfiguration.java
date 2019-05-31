package com.yclouds.myhelper.plugins.advice;

import com.google.common.collect.Lists;
import com.yclouds.myhelper.web.error.code.BaseError;
import com.yclouds.myhelper.web.response.ApiResp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller增强处理配置，用于同一异常处理
 *
 * @author ye17186
 * @version 2019/5/6 15:16
 */
@SuppressWarnings("unused")
@Slf4j
@Configuration
@EnableConfigurationProperties(ControllerAdviceProperties.class)
@ConditionalOnProperty(prefix = "myhelper.plugins.controller-advice", name = "enabled", havingValue = "true")
@ControllerAdvice
public class ControllerAdviceConfiguration {

    /**
     * 参数绑定异常或者参数检验失败异常
     *
     * @return 参数不合法
     * @see org.springframework.validation.annotation.Validated
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    protected ApiResp handleOtherEx(HttpServletRequest request, Exception ex) {
        List<String> list = Lists.newArrayList();
        if (ex instanceof BindException) {
            BindException e1 = ((BindException) ex);
            if (e1.hasErrors()) {
                list = buildErrorMsg(e1.getFieldErrors());
            }
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e2 = (MethodArgumentNotValidException) ex;
            if (e2.getBindingResult().hasFieldErrors()) {
                list = buildErrorMsg(e2.getBindingResult().getFieldErrors());
            }
        }
        log.warn("[非法请求参数] url: {}", request.getRequestURL());
        return ApiResp.retFail(BaseError.SYSTEM_ARGUMENT_NOT_VALID, list);
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
        log.warn("[请求方法不支持] url: {}，method: {}", request.getRequestURL(), method);

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

        log.error("[系统异常] url: {}", request.getRequestURL(), ex);
        return ApiResp.retFail(BaseError.SYSTEM_EXCEPTION);
    }

    /**
     * 收集错误信息
     *
     * @param errors 错误对象
     * @return 错误信息列表
     */
    private List<String> buildErrorMsg(List<FieldError> errors) {
        List<String> msg = Lists.newArrayList();
        if (!ObjectUtils.isEmpty(errors)) {
            errors.forEach(
                item -> msg.add(item.getDefaultMessage()));
        }
        return msg;
    }
}
