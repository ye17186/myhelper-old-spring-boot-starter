package com.yclouds.myhelper.utils;

/**
 * @author ye17186
 * @version 2018/6/29 12:12
 */
@SuppressWarnings("unused")
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private StringUtils() {
    }

    /**
     * 数字左补齐零
     *
     * @param num 原数字
     * @param len 补齐后的长度
     * @return 左补零字符串
     */
    public static String leftPadZero(int num, int len) {
        return StringUtils.leftPad(String.valueOf(num), len, '0');
    }
}
