package com.yclouds.myhelper.sequence;

import com.yclouds.myhelper.utils.IdGenUtils;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 基于内存的Seq计数工厂
 *
 * @author ye17186
 * @version 2019/3/22 17:12
 */
@SuppressWarnings("unused")
public class MemorySeqCounter extends SeqCounter {

    /**
     * 当前seq
     */
    private static AtomicLong currentSeq = new AtomicLong(1);

    /**
     * 获取下一个Seq
     *
     * @return 下一个Seq
     */
    public static long nextSeq() {
        long seq = currentSeq.getAndIncrement();
        return seq % IdGenUtils.maxSeq;
    }

    /**
     * 获取指定个数Seq
     *
     * @param size 个数
     * @return 新ID数组
     */
    public static long[] nextSeq(int size) {
        long maxSeq = currentSeq.addAndGet(size);
        return nextSeq(maxSeq, maxSeq - size);
    }
}