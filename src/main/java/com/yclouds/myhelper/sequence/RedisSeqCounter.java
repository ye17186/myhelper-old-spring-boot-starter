package com.yclouds.myhelper.sequence;

import com.yclouds.myhelper.utils.IdGenUtils;
import com.yclouds.myhelper.utils.RedisUtils;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

/**
 * 基于内存的Seq计数工厂
 *
 * @author ye17186
 * @version 2019/3/25 14:27
 */
@SuppressWarnings("unused")
public class RedisSeqCounter extends SeqCounter {

    /**
     * Redis计数器key
     */
    private static final String counterKey = "REDIS_ID_COUNTER_KEY";
    private static RedisAtomicLong counter = null;

    static {
        RedisConnectionFactory factory = RedisUtils.redisTemplate.getConnectionFactory();
        if (factory != null) {
            counter = new RedisAtomicLong(counterKey, factory);
        }
    }

    /**
     * 获取下一个Seq
     *
     * @return 新Seq
     */
    public static long nextSeq() {
        long seq = counter.addAndGet(1);
        return seq % IdGenUtils.maxSeq;
    }

    /**
     * 获取指定个数Seq
     *
     * @param size 个数
     * @return 新Seq数组
     */
    public static long[] nextSeq(int size) {
        long maxSeq = RedisUtils.increment(counterKey, size);
        return nextSeq(maxSeq, maxSeq - size);
    }
}
