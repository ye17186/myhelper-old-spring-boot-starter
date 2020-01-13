package com.yclouds.myhelper.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

/**
 * Spring上下文工具类。注意：使用之前，请确保ApplicationContext已成功完成初始化。
 *
 * @author ye17186
 * @version 2019/2/22 11:28
 */
@Slf4j
public class SpringUtils {

    private SpringUtils() {
    }

    private static ApplicationContext context = null;

    /**
     * 初始化Spring上下文
     *
     * @param ctx 上下文对象
     */
    static void initContext(ApplicationContext ctx) {
        if (ctx == null) {
            log.warn("ApplicationContext is null.");
            return;
        }
        context = ctx;
    }


    /**
     * 根据类型获取Bean
     *
     * @param cls Bean类
     * @param <T> Bean类型
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> cls) {
        return context == null ? null : context.getBean(cls);
    }

    /**
     * 根据名称获取Bean
     *
     * @param name Bean名称
     * @return Bean对象
     */
    public static Object getBean(String name) {
        return context == null ? null : context.getBean(name);
    }

    /**
     * 根据Bean名称和类获取Bean对象
     *
     * @param name Bean名称
     * @param cls Bean类
     * @param <T> Bean类型
     * @return Bean对象
     */
    public static <T> T getBean(String name, Class<T> cls) {
        return context == null ? null : context.getBean(name, cls);
    }
}
