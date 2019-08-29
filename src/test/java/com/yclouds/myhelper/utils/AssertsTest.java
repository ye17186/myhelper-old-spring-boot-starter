package com.yclouds.myhelper.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yclouds.myhelper.exception.LogicException;
import com.yclouds.myhelper.web.error.code.BaseEnumError;
import org.junit.Assert;
import org.junit.Test;

/**
 * com.yclouds.myhelper.utils.AssertsTest
 *
 * @author ye17186
 * @version 2019/8/29 9:40
 */
public class AssertsTest {

    @Test
    public void notNull() {

        try {
            Asserts.notNull(null, BaseEnumError.SYSTEM_EXCEPTION);
        } catch (LogicException e) {
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getCode(), e.getCode());
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getMsg(), e.getMsg());
            Assert.assertNull(e.getDetail());
        }
    }

    @Test
    public void isNull() {

        try {
            Asserts.isNull(new Object(), BaseEnumError.SYSTEM_EXCEPTION);
        } catch (LogicException e) {
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getCode(), e.getCode());
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getMsg(), e.getMsg());
            Assert.assertNull(e.getDetail());
        }
    }

    @Test
    public void isTrue() {

        try {
            Asserts.isTrue(false, BaseEnumError.SYSTEM_EXCEPTION);
        } catch (LogicException e) {
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getCode(), e.getCode());
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getMsg(), e.getMsg());
            Assert.assertNull(e.getDetail());
        }
    }

    @Test
    public void isFalse() {

        try {
            Asserts.isTrue(true, BaseEnumError.SYSTEM_EXCEPTION);
        } catch (LogicException e) {
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getCode(), e.getCode());
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getMsg(), e.getMsg());
            Assert.assertNull(e.getDetail());
        }
    }

    @Test
    public void notEmpty() {

        try {
            Asserts.notEmpty(Maps.newHashMap(), BaseEnumError.SYSTEM_EXCEPTION);
        } catch (LogicException e) {
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getCode(), e.getCode());
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getMsg(), e.getMsg());
            Assert.assertNull(e.getDetail());
        }
    }

    @Test
    public void notEmpty1() {

        try {
            Asserts.notEmpty(Lists.newArrayList(), BaseEnumError.SYSTEM_EXCEPTION);
        } catch (LogicException e) {
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getCode(), e.getCode());
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getMsg(), e.getMsg());
            Assert.assertNull(e.getDetail());
        }
    }

    @Test
    public void hasLength() {

        try {
            Asserts.hasLength("", BaseEnumError.SYSTEM_EXCEPTION);
        } catch (LogicException e) {
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getCode(), e.getCode());
            Assert.assertEquals(BaseEnumError.SYSTEM_EXCEPTION.getMsg(), e.getMsg());
            Assert.assertNull(e.getDetail());
        }
    }
}