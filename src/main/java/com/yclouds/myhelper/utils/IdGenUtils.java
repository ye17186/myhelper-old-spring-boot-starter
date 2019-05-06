package com.yclouds.myhelper.utils;

import com.yclouds.myhelper.sequence.MemorySeqCounter;
import com.yclouds.myhelper.sequence.RedisSeqCounter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

/**
 * ID生成器
 * <br>序列号利用AtomicLong来生成，保证毫秒内不重复
 * <br>此ID生成器不保证ID的绝对递增，但可以保证趋势递增（因为高位依赖于时间）
 *
 * @author ye17186
 * @version 2019/3/22 15:16
 */
@SuppressWarnings("unused")
@Slf4j
public class IdGenUtils {

    private IdGenUtils() {
    }

    private static final int YEAR_LEN = 2;
    private static final int DAY_LEN = 3;
    private static final String TIME_FORMAT = "HHmmssSSS";
    private static final int SEQ_LEN = 4;

    /**
     * 最大seq数量
     */
    public static final int maxSeq = (int) Math.pow(10, SEQ_LEN);

    /**
     * 基于内存生成唯一ID
     *
     * @return 全局唯一ID
     */
    public static long nextIdByMem() {

        long seq = MemorySeqCounter.nextSeq();
        return convert(seq);
    }

    /**
     * 基于Redis生成唯一ID
     *
     * @return 全局唯一ID
     */
    public static long nextIdByRedis() {
        long seq = RedisSeqCounter.nextSeq();
        return convert(seq);
    }

    public static String uuid() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }

    /**
     * SEQ转ID
     * <br>
     * 随机数利用AtomicLong来实现， ID格式：1-2位：年份；3-5：年中第N天；6-14：HHmmssSSS：15-18：自增随机数
     *
     * @param seq 源Seq
     * @return 系统ID
     */
    private static long convert(long seq) {

        LocalDateTime now = LocalDateTime.now();

        String year = StringUtils.right(String.valueOf(now.getYear()), YEAR_LEN);
        String day = StringUtils.leftPadZero(now.getDayOfYear(), DAY_LEN);
        String time = now.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
        String seqStr = StringUtils.leftPadZero((int) seq, SEQ_LEN);

        return Long.valueOf(year + day + time + seqStr);
    }
}
