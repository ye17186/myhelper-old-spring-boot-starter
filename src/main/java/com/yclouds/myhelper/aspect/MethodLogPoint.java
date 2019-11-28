package com.yclouds.myhelper.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * com.yclouds.myhelper.aspect.MethodLogPoint 日志记录注解
 *
 * @author ye17186
 * @version 2019/6/28 15:03
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodLogPoint {

    /**
     * 操作名
     */
    String action() default "unknown";

    /**
     * 敏感参数
     */
    String[] sensitiveParams() default {};

    /**
     * 忽略响应
     * <p>如果{@code ignoreResult}为true，日志中将不记录方法的返回结果，否则会记录</p>
     */
    boolean ignoreResult() default false;

    /**
     *
     * 是否包含Http请求相关消息
     * <p>如果{@code includeHttpRequest}为true，日志中会记录发送本次Http请求客户端的相关信息</p>
     */
    boolean includeHttpRequest() default false;

}
