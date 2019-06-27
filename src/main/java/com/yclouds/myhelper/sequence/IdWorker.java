package com.yclouds.myhelper.sequence;

import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;

/**
 * com.yclouds.myhelper.sequence.IdWorker
 *
 * @author ye17186
 * @version 2019/6/25 13:44
 */
@Slf4j
public class IdWorker {

    private static AtomicBoolean initFlg = new AtomicBoolean(false);

    /**
     * 基于SnowFlake算法的ID生成器
     */
    public static SnowSequence WORKER = new SnowSequence();

    /**
     * 初始化WORKER
     * <p>
     * 如需手动设置dateCenterId、workId，最好在启动项目时就调用此方法，来完成WORKER的初始化
     * </p>
     *
     * @param dataCenterId 数据中心ID
     * @param workerId 机器ID
     */
    public static void initWorker(long dataCenterId, long workerId) {
        if (!initFlg.getAndSet(true)) {
            // 第一次初始化
            WORKER = new SnowSequence(dataCenterId, workerId);
        } else {
            // 已经初始化过一次，则打印警告日志，不做其他处理
            log.warn("IdWorker only allowed to be initialized once.");
        }
    }

    /**
     * 获取下一个long类型ID
     *
     * @return 下一个long类型ID
     */
    public static long nextId() {
        return WORKER.nextId();
    }

    /**
     * 获取下一个String类型ID
     * <p>
     * 部分系统在设计时，ID采用的是字符串
     * </p>
     *
     * @return 下一个String类型的ID
     */
    public static String nextIdStr() {
        return String.valueOf(WORKER.nextId());
    }

    /**
     * 基于时间的String类型ID
     * @return
     */
    public static String nextTimeId() {
        return WORKER.nextTimeId();
    }
}
