package com.yclouds.myhelper.web.convert;

import com.yclouds.myhelper.utils.DateUtils;
import com.yclouds.myhelper.utils.StringUtils;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import org.springframework.core.convert.converter.Converter;

/**
 * @author ye17186
 * @version 2019/5/29 17:04
 */
public class StringToYearMonthConverter implements Converter<String, YearMonth> {

    private String pattern;

    public StringToYearMonthConverter() {
    }

    public StringToYearMonthConverter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public YearMonth convert(@Nonnull String source) {
        YearMonth yearMonth = null;
        if (StringUtils.isNotEmpty(source)) {
            String format = StringUtils.isEmpty(pattern) ? DateUtils.PATTERN_DATE_01 : pattern;
            yearMonth = YearMonth.parse(source, DateTimeFormatter.ofPattern(format));
        }
        return yearMonth;
    }
}
