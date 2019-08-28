package com.yclouds.myhelper.web.valid;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

/**
 * 参数校验工具类
 *
 * @author ye17186
 * @version 2019/6/4 17:37
 */
@Slf4j
public class ValidUtils {

    private ValidUtils() {
    }

    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        if (validator == null) {
            log.warn("Validator has not been installed.");
        }
    }

    /**
     * 分组校验Bean
     * <pre>此方式下校验Bean不通过时，会抛出异常LogicArgNoValidException</pre>
     *
     * @param bean 待校验的Bean对象
     * @param clz 分组Class
     * @see org.springframework.validation.annotation.Validated
     * @see LogicArgNoValidException
     */
    public static void valid(Object bean, Class<?>... clz) {

        Set<ConstraintViolation<Object>> violationSet = validator.validate(bean, clz);
        if (!CollectionUtils.isEmpty(violationSet)) {
            throw new LogicArgNoValidException(
                violationSet.stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList()));

        }
    }

    /**
     * 静默的方式分组校验Bean
     * <pre>此方式下校验Bean不通过时，不会抛出异常，而是直接返回异常信息列表</pre>
     * <pre>Bean校验通过的话，会返回一个null对象</pre>
     *
     * @param bean 待校验的Bean对象
     * @param clz 分组Class
     * @return 异常信息列表
     *
     * @see org.springframework.validation.annotation.Validated
     */
    public static List<String> validSilent(Object bean, Class<?>... clz) {

        List<String> errors = null;
        Set<ConstraintViolation<Object>> violationSet = validator.validate(bean, clz);
        if (!CollectionUtils.isEmpty(violationSet)) {
            errors = violationSet.stream().map(ConstraintViolation::getMessage).collect(
                Collectors.toList());
        }
        return errors;
    }
}
