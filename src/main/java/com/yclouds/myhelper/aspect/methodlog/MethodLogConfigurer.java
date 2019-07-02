package com.yclouds.myhelper.aspect.methodlog;

import com.yclouds.myhelper.aspect.methodlog.model.MethodLogModel;

/**
 * @author ye17186
 * @version 2019/7/1 10:18
 */
public interface MethodLogConfigurer {

    default void afterReturn(MethodLogModel log) {
    }

    default void afterThrow(MethodLogModel log) {
    }
}
