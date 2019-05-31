package com.yclouds.myhelper.web.convert;

import com.yclouds.myhelper.utils.DateUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nonnull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * @author ye17186
 * @version 2019/5/31 13:45
 */
public class StringToDateConverter implements Converter<String, Date> {

    /**
     * 日期时间格式
     */
    private String dateTimePattern;

    /**
     * 日期格式
     */
    private String datePattern;

    public StringToDateConverter() {
    }

    public StringToDateConverter(String dateTimePattern) {
        this.dateTimePattern = dateTimePattern;
    }

    public StringToDateConverter(String dateTimePattern, String datePattern) {
        this.dateTimePattern = dateTimePattern;
        this.datePattern = datePattern;
    }

    @Override
    public Date convert(@Nonnull String source) {
        Date target = null;
        if (!StringUtils.isEmpty(source)) {
            try {
                String format;
                if (source.length() > 10) {
                    format = StringUtils.isEmpty(dateTimePattern) ? DateUtils.PATTERN_DATETIME_01
                        : dateTimePattern;
                    target = new SimpleDateFormat(format).parse(source);
                } else {
                    format = StringUtils.isEmpty(datePattern) ? DateUtils.PATTERN_DATE_01: datePattern;
                    target = new SimpleDateFormat(format).parse(source);
                }
            } catch (ParseException e) {
                throw new RuntimeException(String.format("parser %s to Date fail.", source));
            }
        }
        return target;
    }
}
