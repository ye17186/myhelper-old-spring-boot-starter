package com.yclouds.myhelper.web.valid;

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
@SuppressWarnings("unused")
@Slf4j
public class ValidUtils {

    private ValidUtils() {
    }

    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        checkValidator();
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

        if (checkValidator()) {
            Set<ConstraintViolation<T>> violationSet = validator.validate(bean, clz);
            if (!CollectionUtils.isEmpty(violationSet)) {
                throw new LogicArgNoValidException(
                    violationSet.stream().map(ConstraintViolation::getMessage)
                        .collect(Collectors.toList()));
            }
        }
    }

    /**
     * 校验Validator是否被启用
     *
     * @return true=启用; false=未启用
     */
    private static boolean checkValidator() {

        boolean result = true;

        if (validator == null) {
            result = false;
            log.warn("Validator has not been installed.");
        }
        return result;
    }
}
