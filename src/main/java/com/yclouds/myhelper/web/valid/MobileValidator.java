package com.yclouds.myhelper.web.valid;

import com.yclouds.myhelper.utils.StringUtils;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 手机号参数校验处理类
 *
 * @author ye17186
 * @version 2019/3/6 15:56
 */
@Slf4j
public class MobileValidator implements ConstraintValidator<MobileValid, String> {

    /**
     * 手机号正则
     */
    private String regex = "^1[3|4|5|7|8][0-9]\\\\d{4,8}$";

    private Pattern pattern = Pattern.compile(regex);


    @Override
    public void initialize(MobileValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        boolean isValid = true;

        if (StringUtils.isNotEmpty(value)) {
            isValid = pattern.matcher(value).matches();
        }
        return isValid;
    }
}
