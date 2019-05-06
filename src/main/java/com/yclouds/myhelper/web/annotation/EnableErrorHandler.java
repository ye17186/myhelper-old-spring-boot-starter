package com.yclouds.myhelper.web.annotation;

import com.yclouds.myhelper.web.error.handler.YErrorHandler;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 错误处理类，用于同一异常处理
 *
 * @author ye17186
 * @version 2019/4/29 14:37
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(YErrorHandler.class)
public @interface EnableErrorHandler {

}
