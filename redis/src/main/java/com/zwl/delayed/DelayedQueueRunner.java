package com.zwl.delayed;

import cn.hutool.extra.spring.SpringUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 描述：执行延迟队列处理
 *
 * @author zwl
 * @since 2022/6/24 11:00
 **/
@Component
@Slf4j
public class DelayedQueueRunner implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {

    @Autowired
    private IDelayedQueue<Object> iDelayedQueue;

    private ThreadPoolExecutor threadPoolExecutor;

    public void run() {
        log.info("start redis delayed task run...........");
        long count = Arrays.stream(DelayTaskEnum.values()).count();
        threadPoolExecutor = new ThreadPoolExecutor((int) count, (int) count,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat("redis-delayed-pool-%d").build());
        for (DelayTaskEnum delayTaskEnum : DelayTaskEnum.values()) {
            threadPoolExecutor.execute(() -> {
                while (true) {
                    try {
                        Object take = iDelayedQueue.take(delayTaskEnum.getName());
                        SpringUtil.getBean(delayTaskEnum.getBeanCLass()).execute(take);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.error("延迟队列异常终端：{}", e.getMessage());
                    }
                }
            });
        }
    }

    public void stop() {
        log.info("redis delayed pool shutdown...........");
        threadPoolExecutor.shutdown();
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        run();
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        stop();
    }
}
