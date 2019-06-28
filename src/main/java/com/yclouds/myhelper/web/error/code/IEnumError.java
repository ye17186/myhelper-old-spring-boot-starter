package com.yclouds.myhelper.web.error.code;

/**
 * com.yclouds.myhelper.exception.IEnumError
 * <p>接口错误响应码可以设计成一个枚举，并实现{@link IEnumError}接口<p/>
 *
 * @author ye17186
 * @version 2019/6/28 14:00
 */
public interface IEnumError {

    /**
     * 获取错误码
     */
    int getCode();

    /**
     * 获取错误信息
     */
    String getMsg();
}
