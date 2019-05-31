package com.yclouds.myhelper.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 基于JDK8的时间处理工具类
 *
 * @author ye17186
 * @version 2019/5/29 13:43
 */
@SuppressWarnings("unused")
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private DateUtils() {
    }

    public static final String PATTERN_DATETIME_01 = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATETIME_02 = "yyyyMMddHHmmss";
    public static final String PATTERN_DATE_01 = "yyyy-MM-dd";
    public static final String PATTERN_DATE_02 = "yyyyMMdd";
    public static final String PATTERN_TIME_01 = "HH:mm:ss";
    public static final String PATTERN_TIME_02 = "HH:mm:ss";
    public static final String PATTERN_MONTH_01 = "yyyy-MM";
    public static final String PATTERN_MONTH_02 = "yyyyMM";

    /**
     * Date转LocalDateTime
     *
     * @param date Date时间
     * @return LocalDateTime时间
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转Date
     *
     * @param dateTime LocalDateTime时间
     * @return Date时间
     */
    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前时间的yyyy-MM-dd HH:mm:ss格式的时间字符串
     *
     * @return 当前时间字符串
     */
    public static String format() {
        return format(PATTERN_DATETIME_01);
    }

    /**
     * 获取dataTime时间的yyyy-MM-dd HH:mm:ss格式的时间字符串
     *
     * @param dataTime 指定时间
     * @return dataTime的时间字符串
     */
    public static String format(LocalDateTime dataTime) {
        return format(dataTime, PATTERN_DATETIME_01);
    }

    /**
     * 获取当前时间pattern格式的字符串
     *
     * @param pattern 时间格式
     * @return pattern格式的时间字符串
     */
    public static String format(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取dateTime时间的pattern格式的字符串
     *
     * @param dateTime 指定时间
     * @param pattern 时间格式
     * @return pattern格式的时间字符串
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * yyyy-MM-dd HH:mm:ss格式的时间字符串转LocalDateTime时间
     *
     * @param dateTimeStr 时间字符串
     * @return 时间
     */
    public static LocalDateTime parse(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(PATTERN_DATETIME_01));
    }

    /**
     * 时间字符串转LocalDateTime时间
     *
     * @param dateTimeStr 时间字符串
     * @param pattern 时间格式
     * @return 时间
     */
    public static LocalDateTime parse(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 当天的开始时间,时分秒为00:00:00
     *
     * @return 当天的开始时间
     */
    public static LocalDateTime startOfDate() {
        return startOfDate(LocalDate.now());
    }

    /**
     * 指定日期的开始时间,时分秒为00:00:00
     *
     * @param date 指定日期
     * @return 指定日期的开始时间
     */
    public static LocalDateTime startOfDate(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIN);
    }


    /**
     * 当天的结束时间,时分秒为23:59:59
     *
     * @return 当天的结束时间
     */
    public static LocalDateTime endOfDate() {
        return endOfDate(LocalDate.now());
    }

    /**
     * 指定日期的结束时间，时分秒为23:59:59
     *
     * @param date 指定日期
     * @return 指定日期的结束时间
     */
    public static LocalDateTime endOfDate(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MAX);
    }

    /**
     * 计算两个时间的时间差，Duration.toHour等方法，可转成具体天数、小时数等等
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 时间差
     */
    public static Duration between(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end);
    }

    /**
     * 计算两个时间相隔的天数
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 相隔天数
     */
    public static long betweenByDays(LocalDateTime start, LocalDateTime end) {
        return between(start, end).toDays();
    }

    /**
     * 计算两个时间相隔好描述
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 相隔毫秒数
     */
    public static long betweenByMillis(LocalDateTime start, LocalDateTime end) {
        return between(start, end).toMillis();
    }
}
