package com.yclouds.myhelper.sequence;

import com.yclouds.myhelper.utils.IdGenUtils;

/**
 * @author ye17186
 * @version 2019/3/25 15:19
 */
class SeqCounter {

    static long[] nextSeq(long maxSeq, long minSeq) {
        int size = (int) (maxSeq - minSeq);

        long[] ids = new long[size];
        for (int i = 0; i < size; i++) {
            ids[i] = (minSeq + i) % IdGenUtils.maxSeq;
        }
        return ids;
    }
}
