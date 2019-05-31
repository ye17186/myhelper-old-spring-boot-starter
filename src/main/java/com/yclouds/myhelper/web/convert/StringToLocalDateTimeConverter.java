package com.yclouds.myhelper.web.convert;

import com.yclouds.myhelper.utils.DateUtils;
import com.yclouds.myhelper.utils.StringUtils;
import java.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

/**
 * @author ye17186
 * @version 2019/5/29 9:25
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private String pattern;

    public StringToLocalDateTimeConverter() {
    }

    public StringToLocalDateTimeConverter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public LocalDateTime convert(@NonNull String source) {

        LocalDateTime localDateTime = null;

        if (StringUtils.isNotEmpty(source)) {
            String format = StringUtils.isEmpty(pattern) ? DateUtils.PATTERN_DATETIME_01 : pattern;
            localDateTime = DateUtils.parse(source, format);
        }
        return localDateTime;
    }
}
