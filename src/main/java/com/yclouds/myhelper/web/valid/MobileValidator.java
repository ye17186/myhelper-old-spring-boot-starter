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
public class MobileValidator implements ConstraintValidator<Mobile, String> {


    private Pattern pattern;

    @Override
    public void initialize(Mobile mobile) {

        pattern = Pattern.compile(mobile.pattern());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean isValid = true;

        if (StringUtils.isNotEmpty(value)) {
            isValid = pattern.matcher(value).matches();
        }
        return isValid;
    }
}
