package com.zwl.delayed;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 描述：延迟队列实现
 *
 * @author zwl
 * @since 2022/6/24 10:50
 **/
@Component
public class DefaultDelayedQueue<Object> implements IDelayedQueue<Object> {

    @Autowired
    Redisson redisson;

    @Override
    public void addDelayedQueue(Object msg, Long time, TimeUnit timeUnit, String queueName) {
        RBlockingQueue<Object> blockingQueue = redisson.getBlockingQueue(queueName, JsonJacksonCodec.INSTANCE);
        RDelayedQueue<Object> delayedQueue = redisson.getDelayedQueue(blockingQueue);
        delayedQueue.offer(msg, time, timeUnit);
    }

    @Override
    public Object take(String queueName) throws InterruptedException {
        RBlockingQueue<Object> blockingQueue = redisson.getBlockingQueue(queueName, JsonJacksonCodec.INSTANCE);
        return blockingQueue.take();
    }
}
