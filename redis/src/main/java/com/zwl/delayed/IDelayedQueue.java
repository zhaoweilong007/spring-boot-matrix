package com.zwl.delayed;

import java.util.concurrent.TimeUnit;

/**
 * 描述：延迟队列接口
 *
 * @author zwl
 * @since 2022/6/24 10:47
 **/
public interface IDelayedQueue<T> {

    /**
     * 添加消息到到延迟队列
     */
    void addDelayedQueue(T msg, Long time, TimeUnit timeUnit, String queueName);


    /**
     * 获取延迟队列消息
     */
    T take(String queueName) throws InterruptedException;

}
