package com.yclouds.myhelper.web.valid;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 手机号校验注解
 *
 * @author ye17186
 * @version 2019/7/18 15:34
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MobileValidator.class})
public @interface Mobile {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 手机号正则
     */
    String pattern() default "^1[3|4|5|7|8][0-9]\\\\d{4,8}$";
}
