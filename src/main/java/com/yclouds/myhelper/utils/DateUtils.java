package com.yclouds.myhelper.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ye17186
 * @version 2019/2/19 16:35
 */
@SuppressWarnings("unused")
public class DateUtils {

    private DateUtils() {
    }

    /**
     * 日期时间格式01：yyyy-MM-dd HH:mm:ss
     */
    public static final String FMT_DATETIME_01 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期时间格式02：yyyyMMddHHmmss
     */
    public static final String FMT_DATETIME_02 = "yyyyMMddHHmmss";

    /**
     * 日期格式01：yyyy-MM-dd
     */
    public static final String FMT_DATE_01 = "yyyy-MM-dd";

    /**
     * 时间格式01：yyyy-MM-dd
     */
    public static final String FMT_TIME_01 = "HH:mm:ss";

    /**
     * LocalDateTime 日期格式化
     *
     * @param dateTime 时间
     * @return 默认格式的时间字符串
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, FMT_DATETIME_01);
    }

    /**
     * LocalDateTime 日期格式化
     *
     * @param dateTime 时间
     * @param pattern 格式
     * @return 指定格式的时间字符串
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * Instant日期格式化
     *
     * @param instant Instant时间
     * @return 默认格式的时间字符串
     */
    public static String format(Instant instant) {
        return format(instant, FMT_DATETIME_01);
    }

    /**
     * Instant日期格式化
     *
     * @param instant Instant时间
     * @param pattern 日期格式
     * @return 指定格式的时间字符串
     */
    public static String format(Instant instant, String pattern) {
        return format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()), pattern);
    }

    /**
     *
     */
    public static Date endOfDay() {

        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取当天的起始时间
     *
     * @return 当天起始时间
     */
    public static LocalDateTime startOfDay8() {
        return startOfDay8(LocalDate.now());
    }

    /**
     * 获取指定日期的起始时间
     *
     * @param localDate 指定日期
     * @return 指定日期起始时间
     */
    public static LocalDateTime startOfDay8(LocalDate localDate) {

        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    /**
     * 获取当天的结束时间
     *
     * @return 当天结束时间
     */
    public static LocalDateTime endOfDay8() {
        return endOfDay8(LocalDate.now());
    }

    /**
     * 获取指定日期的结束时间
     *
     * @param localDate 指定日期
     * @return 指定日期结束时间
     */
    public static LocalDateTime endOfDay8(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }

    /**
     * 获取当前时间字符串
     *
     * @return yyyy-MM-dd HH:mm:ss时间字符串
     */
    public static String now() {
        return format(LocalDateTime.now());
    }

    /**
     * 获取当前时间指定格式的时间字符串
     *
     * @param pattern 时间格式
     * @return 指定格式时间字符串
     */
    public static String now(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }
}
