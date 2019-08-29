package com.yclouds.myhelper.utils;

import com.yclouds.myhelper.exception.LogicException;
import com.yclouds.myhelper.web.error.code.BaseEnumError;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步任务获取处理结果工具类
 *
 * @author yemeng-lhq
 * @version 2019/8/20 14:32
 */
public class FutureUtils {

    private FutureUtils() {
    }

    private static final Logger logger = LoggerFactory.getLogger(FutureUtils.class);


    /**
     * 默认获取异步结果的时间间隔，单位毫秒
     */
    private static final int DEFAULT_WAIT_MILLISECONDS = 5;

    /**
     * 默认获取异步结果的超时时间，单位秒
     */
    private static final int DEFAULT_TIMEOUT_SECOND = 30;

    /**
     * 获取异步任务处理结果
     *
     * @param future 异步任务
     * @param <T> 处理结果泛型
     * @return 处理结果
     */
    public static <T> T get(Future<T> future) {
        return get(future, DEFAULT_WAIT_MILLISECONDS, DEFAULT_TIMEOUT_SECOND);
    }

    /**
     * 获取异步任务处理结果
     *
     * @param future 异步任务
     * @param timeout 指定时间内，异步任务仍未结束，则停止获取异步结果，并抛出{@link LogicException}
     * @param <T> 处理结果泛型
     * @return 处理结果
     */
    public static <T> T get(Future<T> future, int timeout) {
        return get(future, DEFAULT_WAIT_MILLISECONDS, timeout);
    }

    /**
     * 获取异步任务处理结果
     *
     * @param future 异步任务
     * @param wait 如果任务未结束，则等待指定的毫秒后，再尝试获取异步结果
     * @param timeout 指定时间内，异步任务仍未结束，则停止获取异步结果，并抛出{@link LogicException}
     */
    public static <T> T get(Future<T> future, int wait, int timeout) {
        if (wait < 0) {
            wait = DEFAULT_WAIT_MILLISECONDS;
        }

        for (int time = 0; time <= timeout * 1000 || timeout <= 0; time += wait) {
            if (future.isDone()) {
                try {
                    return future.get();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                    throw new LogicException(BaseEnumError.SYSTEM_ASYNC_RESULT_GET_EX);
                } catch (ExecutionException e) {
                    logger.warn(e.getMessage(), e);
                    if (e.getCause() instanceof LogicException) {
                        throw (LogicException) e.getCause();
                    }
                    throw new LogicException(BaseEnumError.SYSTEM_ASYNC_RESULT_GET_EX);
                }
            } else if (wait > 0) {
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException ignore) {
                }
            }
        }
        throw new LogicException(BaseEnumError.SYSTEM_ASYNC_RESULT_GET_TIMEOUT);
    }
}
