package com.yclouds.myhelper.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

/**
 * com.yclouds.myhelper.utils.DateUtilsTest
 *
 * @author yemeng-lhq
 * @version 2019/8/29 9:54
 */
public class DateUtilsTest {


    /**
     * 2019年8月15日12点00分00秒
     */
    private long stamp = 1565841600000L;

    private LocalDateTime getTestDateTime() {

        // 2019年8月15日12点00分00秒
        Instant instant = Instant.ofEpochMilli(stamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    @Test
    public void toLocalDateTime() {

        Date date = new Date(stamp);
        LocalDateTime dateTime = DateUtils.toLocalDateTime(date);
        LocalDateTime target = getTestDateTime();
        Assert.assertEquals(0, target.compareTo(dateTime));
    }

    @Test
    public void toLocalDateTime1() {

        LocalDateTime dateTime = DateUtils.toLocalDateTime(stamp);
        LocalDateTime target = getTestDateTime();
        Assert.assertEquals(0, target.compareTo(dateTime));
    }

    @Test
    public void toDate() {

        LocalDateTime dateTime = getTestDateTime();
        Date date = DateUtils.toDate(dateTime);

        Date target = new Date(stamp);
        Assert.assertEquals(0, target.compareTo(date));
    }

    @Test
    public void getNow() {

        LocalDateTime now = DateUtils.getNow();
        Assert.assertNotNull(now);
    }

    @Test
    public void format() {

        LocalDateTime dateTime = getTestDateTime();
        String str = DateUtils.format(dateTime);

        Assert.assertEquals("2019-08-15 12:00:00", str);

    }

    @Test
    public void format1() {

        LocalDateTime dateTime = getTestDateTime();
        String str = DateUtils.format(dateTime, "yyyyMMddHHmmss");

        Assert.assertEquals("20190815120000", str);
    }

    @Test
    public void parse() {

        LocalDateTime dateTime = DateUtils.parse("2019-08-15 12:00:00");
        LocalDateTime target = getTestDateTime();

        Assert.assertEquals(0, target.compareTo(dateTime));
    }

    @Test
    public void parse1() {

        LocalDateTime dateTime = DateUtils.parse("20190815120000", "yyyyMMddHHmmss");

        Instant instant = Instant.ofEpochMilli(stamp);
        LocalDateTime target = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        Assert.assertEquals(0, target.compareTo(dateTime));
    }

    @Test
    public void startOfDate() {

        LocalDate localDate = LocalDate.of(2019, 8, 15);
        LocalDateTime localDateTime = DateUtils.startOfDate(localDate);

        Assert.assertEquals(2019, localDateTime.getYear());
        Assert.assertEquals(8, localDateTime.getMonthValue());
        Assert.assertEquals(15, localDateTime.getDayOfMonth());
        Assert.assertEquals(0, localDateTime.getHour());
        Assert.assertEquals(0, localDateTime.getMinute());
        Assert.assertEquals(0, localDateTime.getSecond());


    }

    @Test
    public void endOfDate() {

        LocalDate localDate = LocalDate.of(2019, 8, 15);
        LocalDateTime localDateTime = DateUtils.endOfDate(localDate);

        Assert.assertEquals(2019, localDateTime.getYear());
        Assert.assertEquals(8, localDateTime.getMonthValue());
        Assert.assertEquals(15, localDateTime.getDayOfMonth());
        Assert.assertEquals(23, localDateTime.getHour());
        Assert.assertEquals(59, localDateTime.getMinute());
        Assert.assertEquals(59, localDateTime.getSecond());
    }


    @Test
    public void between() {

        LocalDateTime star = LocalDateTime.of(2019, 8, 15, 0,0,0);
        LocalDateTime end = LocalDateTime.of(2019, 8, 20, 0,0,0);
        Duration duration = DateUtils.between(star, end);
        Assert.assertEquals(5, duration.toDays());
    }

    @Test
    public void betweenByDays() {

        LocalDateTime star = LocalDateTime.of(2019, 8, 15, 0,0,0);
        LocalDateTime end = LocalDateTime.of(2019, 8, 20, 0,0,0);
        long days = DateUtils.betweenByDays(star, end);
        Assert.assertEquals(5, days);
    }
}