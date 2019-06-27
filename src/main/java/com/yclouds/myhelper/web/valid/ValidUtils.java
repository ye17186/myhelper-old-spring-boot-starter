package com.yclouds.myhelper.web.valid;

import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * 参数校验工具类
 *
 * @author ye17186
 * @version 2019/6/4 17:37
 */
@SuppressWarnings("unused")
public class ValidUtils {

    private ValidUtils() {
    }

    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        Assert.notNull(validator, "Validator has not been install.");
    }

    /**
     * 分组校验Bean
     *
     * @param bean 待校验的Bean对象
     * @param clz 分组Class
     * @param <T> Bean类型
     * @see org.springframework.validation.annotation.Validated
     */
    public static <T> void valid(T bean, Class<?>... clz) {

        Set<ConstraintViolation<T>> violationSet = validator.validate(bean, clz);
        if (!CollectionUtils.isEmpty(violationSet)) {
            throw new LogicArgNoValidException(
                violationSet.stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList()));
        }
    }
}
