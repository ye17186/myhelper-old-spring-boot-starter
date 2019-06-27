package com.yclouds.myhelper.sequence;

import com.yclouds.myhelper.utils.StringPool;
import com.yclouds.myhelper.utils.StringUtils;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

/**
 * com.yclouds.myhelper.sequence.AbstractSequence
 *
 * @author ye17186
 * @version 2019/6/26 12:05
 */
@Slf4j
public abstract class AbstractSequence {

    protected static final DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern("yyyyMMddHHmmssSSS");

    /**
     * 上次生产 ID 时间戳
     */
    protected long lastTimestamp = -1L;

    /**
     * 并发控制，序列号
     */
    protected long sequence = 0;

    public abstract long nextId();

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected void throwClockException(long offset) {
        throw new RuntimeException(String
            .format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                offset));
    }

    protected long calcWorkerId(long dataCenterId, long maxWorkerId) {

        StringBuilder mPid = new StringBuilder();
        mPid.append(dataCenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtils.isNotEmpty(name)) {
            /*
             * GET jvmPid
             */
            mPid.append(name.split(StringPool.AT)[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mPid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * 计算数据中心ID
     *
     * @param dataCenterId 数据中心ID
     * @return 数据中心ID
     */
    protected long calcDataCenterId(long dataCenterId) {

        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (
                        ((long) mac[mac.length - 2]) << 8))) >> 6;
                    id = id % (dataCenterId + 1);
                }
            }
        } catch (Exception e) {
            log.warn(" getDataCenterId: " + e.getMessage());
        }
        return id;
    }

}
