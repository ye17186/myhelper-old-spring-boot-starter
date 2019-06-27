package com.yclouds.myhelper.sequence;

import com.yclouds.myhelper.utils.StringUtils;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * com.yclouds.myhelper.sequence.SnowSequence
 * <p>
 * 基于Twitter的Snowflake算法
 * <br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 * <br>
 * <pre>SnowFlake的结构如下(每部分用-分开)：</pre>
 * <pre>0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000</pre>
 * <pre>1位标识：由于long基本类型在Java中是带符号的，正数0，负数1，id一般为正数，所有高1位固定为0</pre>
 * <pre>41位时间戳：此41位时间戳并不是存储当前的时间戳，而是存储当前时间戳与基准时间戳{@code startStamp}的差值</pre>
 * <pre>5位数据中心标识：可根据业务场景，自由调整数据中心位数和机器标识位数</pre>
 * <pre>5位机器标识：可根据业务场景，自由调整数据中心位数和机器标识位数</pre>
 * <pre>12位序列号：毫秒内的计数，12位的计数支持每个节点每毫秒内（同一数据中心、同一机器、同一毫秒）产生4096个ID</pre>
 * 上述合计64位，为一个Long型。
 * </p>
 *
 * @author ye17186
 * @version 2019/6/25 13:46
 */
@Slf4j
public class SnowSequence extends AbstractSequence {

    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动） 当前基准选定为2010-01-01 00:00:00
     */
    @Getter
    private final long startStamp = 1262275200000L;

    /**
     * 机器ID标识位数：5
     */
    private final long workerIdBits = 5L;

    /**
     * 数据中心标识位数：5
     */
    private final long dataCenterIdBits = 5L;

    /**
     * 最大机器ID
     * <p>
     * 默认最大机器ID为31，即最大支持32个机器
     * </p>
     */
    private long maxWorkerId = ~(-1L << workerIdBits);

    /**
     * 最大数据中心ID:31
     * <p>
     * 默认最大数据中心ID为31，即最大支持32个数据中心
     * </p>
     */
    private final long maxDataCenterId = ~(-1L << dataCenterIdBits);

    /**
     * 毫秒内自增位：12
     */
    private final long sequenceBits = 12L;

    /**
     * 机器ID左移位数
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据中心左移位数
     */
    private final long dataCenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间戳左移位数
     */
    @Getter
    public final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /**
     * 序列号掩码，二进制表示为：111111111111
     */
    private final long sequenceMask = ~(-1L << sequenceBits);


    private final long dataCenterId;

    private final long workerId;

    /**
     * 采用默认的数据中心ID、机器ID（根据MAC、IP、PID计算而来）
     */
    public SnowSequence() {
        // this.dataCenterId = calcDataCenterId(maxDataCenterId);
        // this.workerId = calcWorkerId(dataCenterId, maxWorkerId);
        this.dataCenterId = 0L;
        this.workerId = 0L;
    }

    /**
     * 手动指定数据中心ID、机器ID
     *
     * @param dataCenterId 数据中心ID
     * @param workerId 机器ID
     */
    public SnowSequence(long dataCenterId, long workerId) {
        Assert.isTrue(workerId >= 0 && workerId <= maxWorkerId,
            String.format("Worker Id must be between 0 and %d", maxWorkerId));
        Assert.isTrue(dataCenterId >= 0 && dataCenterId <= maxDataCenterId,
            String.format("Data Center Id must be between 0 and %d", maxDataCenterId));
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    /**
     * 生成下一个ID
     *
     * @return 下一个ID
     */
    @Override
    public synchronized long nextId() {

        return next().buildId();
    }

    public String nextTimeId() {

        return next().buildTimeId();
    }

    private synchronized TNode next() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) { // 闰秒
                try {
                    wait(offset << 1); // 阻塞到下一个可以生成Id的时间戳
                    timestamp = timeGen();
                    if (timestamp < lastTimestamp) {
                        throwClockException(offset);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else { // 发生了时间回拨
                throwClockException(offset);
            }

        }

        if (lastTimestamp == timestamp) {

            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 同一毫秒的序列数已经达到最大
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0;
        }

        lastTimestamp = timestamp;


        TNode tn = new TNode();
        tn.setTime(timestamp);
        tn.setDataCenterId(dataCenterId);
        tn.setWorkerId(workerId);
        tn.setSequence(sequence);

        return tn;
    }

    @Getter
    @Setter
    public class TNode {

        private long time;

        private long dataCenterId;

        private long workerId;

        private long sequence;

        public long buildId() {

            // 时间戳部分 | 数据中心部分 | 机器标识部分 | 序列号部分
            return ((time - startStamp) << timestampLeftShift) | (dataCenterId << dataCenterIdShift)
                | (workerId << workerIdShift) | sequence;
        }

        public String buildTimeId() {

            Instant instant = Instant.ofEpochMilli(time);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

            // 数据中心部分 | 机器标识部分 | 序列号部分
            long seq = (dataCenterId << dataCenterIdShift) | (workerId << workerIdShift) | sequence;

            // 5位数据中心+5位机器+12位序列号，表示范围为0-4194303，所以采用7位数字表示
            return localDateTime.format(formatter) + StringUtils
                .leftPad(String.valueOf(seq), 7, '0');
        }
    }
}
