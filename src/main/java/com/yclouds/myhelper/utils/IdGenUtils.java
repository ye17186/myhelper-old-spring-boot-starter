package com.yclouds.myhelper.utils;

import com.yclouds.myhelper.sequence.IdWorker;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;

/**
 * ID生成器
 * <pre>1、基于SnowFlake的分布式ID（long类型）</pre>
 * <pre>2、基于SnowFlake的分布式ID（String类型）</pre>
 * <pre>3、基于SnowFlake的分布式ID变种（显示时间）</pre>
 * <pre>4、UUID</pre>
 *
 * @author ye17186
 * @version 2019/3/22 15:16
 */
@SuppressWarnings("unused")
@Slf4j
public class IdGenUtils {

    private IdGenUtils() {
    }

    /**
     * 生成一个UUID，去掉"-"
     * <p>多线程环境下，使用ThreadLocalRandom获取UUID效果更优</p>
     */
    public static String uuid() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString()
            .replace(StringPool.DASH, StringPool.EMPTY);
    }

    /**
     * 下一个long类型ID
     *
     * @return 下一个ID
     */
    public static long nextId() {
        return IdWorker.nextId();
    }

    /**
     * 下一个String类型ID
     *
     * @return 下一个ID
     */
    public static String nextIdStr() {
        return IdWorker.nextIdStr();
    }

    /**
     * 时间 ID = Time + DateCenter + Worker + Seq
     * <p>例如：可用于商品订单 ID</p>
     */
    public static String nextTimeId() {

        return IdWorker.nextTimeId();
    }

    /**
     * 初始化IdWorker
     *
     * @param dataCenterId 数据中心ID
     * @param workerId 机器ID
     */
    public static void initWorker(long dataCenterId, long workerId) {
        IdWorker.initWorker(dataCenterId, workerId);
    }
}
