package com.yclouds.myhelper.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.text.SimpleDateFormat;
import lombok.extern.slf4j.Slf4j;

/**
 * JSON转换处理工具类
 *
 * @author ye17186
 * @version 2018/6/29 12:06
 */
@Slf4j
@SuppressWarnings("unused")
public class JsonUtils {

    private JsonUtils(){}

    private static ObjectMapper om = new ObjectMapper();

    static {

        // 对象的所有字段全部列入，还是其他的选项，可以忽略null等
        om.setSerializationInclusion(Include.ALWAYS);
        // 设置Date类型的序列化及反序列化格式
        om.setDateFormat(new SimpleDateFormat(DateUtils.PATTERN_DATETIME_01));

        // 忽略空Bean转json的错误
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略未知属性，防止json字符串中存在，java对象中不存在对应属性的情况出现错误
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 注册一个时间序列化及反序列化的处理模块，用于解决jdk8中localDateTime等的序列化问题
        om.registerModule(new JavaTimeModule());
    }

    /**
     * 对象 => json字符串
     *
     * @param obj 源对象
     */
    public static <T> String obj2Json(T obj) {

        String json = null;
        if (obj != null) {
            try {
                json = om.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                log.warn(e.getMessage(), e);
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return json;
    }

    /**
     * json字符串 => 对象
     *
     * @param json 源json串
     * @param clazz 对象类
     * @param <T> 泛型
     */
    public static <T> T json2Obj(String json, Class<T> clazz) {

        return json2Obj(json, clazz, null);
    }

    /**
     * json字符串 => 对象
     *
     * @param json 源json串
     * @param type 对象类型
     * @param <T> 泛型
     */
    public static <T> T json2Obj(String json, TypeReference type) {

        return json2Obj(json, null, type);
    }


    /**
     * json => 对象处理方法
     * <br>
     * 参数clazz和type必须一个为null，另一个不为null
     * <br>
     * 此方法不对外暴露，访问权限为private
     *
     * @param json 源json串
     * @param clazz 对象类
     * @param type 对象类型
     * @param <T> 泛型
     */
    private static <T> T json2Obj(String json, Class<T> clazz, TypeReference type) {

        T obj = null;
        if (!StringUtils.isEmpty(json)) {
            try {
                if (clazz != null) {
                    obj = om.readValue(json, clazz);
                } else {
                    obj = om.readValue(json, type);
                }
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return obj;
    }
}
