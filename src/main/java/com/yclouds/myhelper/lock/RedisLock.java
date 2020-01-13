package com.yclouds.myhelper.lock;

import com.yclouds.myhelper.utils.RedisUtils;

/**
 * 基于Redis实现的分布式锁
 *
 * @author ye17186
 * @version 2019/7/18 17:01
 */
public class RedisLock implements DistributedLock {

    @Override
    public boolean lock(String key, int time) {

        return RedisUtils.setNx(PREFIX + key, key, time);
    }

    @Override
    public boolean unlock(String key) {

        return RedisUtils.del(PREFIX + key);
    }
}
