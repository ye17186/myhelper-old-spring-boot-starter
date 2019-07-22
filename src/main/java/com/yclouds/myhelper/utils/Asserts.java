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

    public static void notNull(@Nullable Object obj, IEnumError enumError) {

        if (obj == null) {
            throw new LogicException(enumError);
        }
    }

    public static void isNull(@Nullable Object obj, IEnumError enumError) {

        if (obj != null) {
            throw new LogicException(enumError);
        }
    }

    public static void isTrue(boolean expression, IEnumError enumError) {

        if (!expression) {
            throw new LogicException(enumError);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, IEnumError enumError) {

        if (CollectionUtils.isEmpty(collection)) {
            throw new LogicException(enumError);
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, IEnumError enumError) {

        if (CollectionUtils.isEmpty(map)) {
            throw new LogicException(enumError);
        }
    }

    public static void hasLength(@Nullable String text, IEnumError enumError) {

        if (StringUtils.isEmpty(text)) {
            throw new LogicException(enumError);
        }
    }
}
