package com.yclouds.myhelper.lock;

/**
 * 分布式锁
 *
 * @author ye17186
 * @version 2019/7/18 17:02
 */
public interface DistributedLock {

    /**
     * 锁名前缀（加锁该前缀后，可以将所有的Redis锁放入一个分组，方便查看）
     */
    String PREFIX = "YC-REDIS-LOCK:";

    /**
     * 加锁
     *
     * @param key 锁名
     * @param time 超时时间
     * @return true=加锁成功；false=加锁失败
     */
    boolean lock(String key, int time);

    /**
     * 解锁
     *
     * @param key 锁名
     * @return true=解锁成功；false=解锁失败
     */
    boolean unlock(String key);
}
