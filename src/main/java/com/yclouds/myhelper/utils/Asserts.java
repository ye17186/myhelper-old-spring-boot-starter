package com.yclouds.myhelper.utils;

import com.yclouds.myhelper.exception.LogicException;
import com.yclouds.myhelper.web.error.code.IEnumError;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

/**
 * 断言工具类
 *
 * @author ye17186
 * @version 2019/7/22 11:14
 */
public class Asserts {

    /**
     * 断言不为null
     *
     * @param obj 断言对象
     * @param enumError 错误信息
     */
    public static void notNull(@Nullable Object obj, IEnumError enumError) {

        if (obj == null) {
            throw new LogicException(enumError);
        }
    }

    /**
     * 断言为null
     *
     * @param obj 断言对象
     * @param enumError 错误信息
     */
    public static void isNull(@Nullable Object obj, IEnumError enumError) {

        if (obj != null) {
            throw new LogicException(enumError);
        }
    }

    /**
     * 断言为真
     *
     * @param expression 断言表达式
     * @param enumError 错误信息
     */
    public static void isTrue(boolean expression, IEnumError enumError) {

        if (!expression) {
            throw new LogicException(enumError);
        }
    }

    /**
     * 断言为假
     *
     * @param expression 断言表达式
     * @param enumError 错误信息
     */
    public static void isFalse(boolean expression, IEnumError enumError) {

        if (expression) {
            throw new LogicException(enumError);
        }
    }

    /**
     * 断言集合对象不为空
     *
     * @param collection 断言对象
     * @param enumError 错误信息
     */
    public static void notEmpty(@Nullable Collection<?> collection, IEnumError enumError) {

        if (CollectionUtils.isEmpty(collection)) {
            throw new LogicException(enumError);
        }
    }

    /**
     * 断言Map对象不为空
     *
     * @param map 断言对象
     * @param enumError 错误信息
     */
    public static void notEmpty(@Nullable Map<?, ?> map, IEnumError enumError) {

        if (CollectionUtils.isEmpty(map)) {
            throw new LogicException(enumError);
        }
    }

    /**
     * 断言字符串不为空
     *
     * @param text 断言对象
     * @param enumError 错误信息
     */
    public static void hasLength(@Nullable String text, IEnumError enumError) {

        if (StringUtils.isEmpty(text)) {
            throw new LogicException(enumError);
        }
    }
}
