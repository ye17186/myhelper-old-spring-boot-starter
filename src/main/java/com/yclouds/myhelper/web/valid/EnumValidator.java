package com.yclouds.myhelper.web.valid;

import com.yclouds.myhelper.utils.StringUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 枚举参数校验处理类
 *
 * @author ye17186
 * @version 2019/3/6 15:56
 */
@Slf4j
public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private EnumValid annotation;

    @Override
    public void initialize(EnumValid constraintAnnotation) {

        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;

        Class<?> cls = annotation.target();
        boolean ignoreEmpty = annotation.ignoreEmpty();

        if (cls.isEnum() && (StringUtils.isNotEmpty(value) || !ignoreEmpty)) {

            Object[] objects = cls.getEnumConstants();
            try {
                Method method = cls.getMethod("name");
                for (Object obj : objects) {
                    Object code = method.invoke(obj);
                    if (StringUtils.isNotEmpty(value) && value.equals(code.toString())) {
                        result = true;
                        break;
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                log.warn("EnumValidator call isValid() method exception.");
                result = false;
            }
        } else {
            result = true;
        }
        return result;
    }
}
