package com.yclouds.myhelper.aspect.methodlog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author ye17186
 * @version 2019/7/1 10:46
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MethodLogConfiguration.class})
public @interface EnableMethodLog {

}
