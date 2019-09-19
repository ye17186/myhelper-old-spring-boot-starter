package com.yclouds.myhelper.aspect.methodlog;

import com.yclouds.myhelper.aspect.methodlog.model.MethodLogModel;

/**
 * @author ye17186
 * @version 2019/7/1 10:18
 */
public interface MethodLogConfigurer {

    /**
     * 方法正常返回后的处理
     *
     * @param logModel 日志对象
     */
    default void afterReturn(MethodLogModel logModel) {
    }

    /**
     * 方法抛出异常后的处理
     */
    default void afterThrow(MethodLogModel logModel) {
    }
}
