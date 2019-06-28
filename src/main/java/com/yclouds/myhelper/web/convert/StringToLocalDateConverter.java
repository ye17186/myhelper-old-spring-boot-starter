package com.yclouds.myhelper.web.convert;

import com.yclouds.myhelper.utils.DateUtils;
import com.yclouds.myhelper.utils.StringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import org.springframework.core.convert.converter.Converter;

/**
 * @author ye17186
 * @version 2019/5/29 16:55
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private String pattern;

    public StringToLocalDateConverter() {
    }

    public StringToLocalDateConverter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public LocalDate convert(@Nonnull String source) {


        LocalDate localDate = null;
        if(StringUtils.isNotEmpty(source)) {
            String format = StringUtils.isEmpty(pattern) ? DateUtils.PATTERN_DATE_01 : pattern;
            localDate = LocalDate.parse(source, DateTimeFormatter.ofPattern(format));
        }
        return localDate;
    }
}
