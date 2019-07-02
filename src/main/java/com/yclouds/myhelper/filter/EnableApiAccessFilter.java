package com.yclouds.myhelper.filter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * com.yclouds.myhelper.filter.EnableApiAccessFilter
 *
 * @author ye17186
 * @version 2019/7/2 13:23
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ApiAccessFilter.class})
public @interface EnableApiAccessFilter {

}
