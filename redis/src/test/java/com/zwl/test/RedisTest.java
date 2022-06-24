package com.zwl.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zwl.delayed.DelayTaskEnum;
import com.zwl.delayed.IDelayedQueue;
import com.zwl.model.SMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/4 18:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisTest {
    @Autowired
    Redisson redisson;

    @Autowired
    IDelayedQueue<Object> iDelayedQueue;

    @Test
    public void pubSub() throws InterruptedException {
        RTopic test = redisson.getTopic("test");
        test.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence channel, String msg) {
                log.info("test listener ........msg:{}", msg);
            }
        });
        for (int i = 0; i < 10; i++) {
            test.publish("hello world");
        }
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }


    @Test
    public void delayedQueue() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            SMessage sMessage = new SMessage();
            sMessage.setSenderId((long) i);
            sMessage.setReceiverId((long) i);
            sMessage.setContent("hello " + i);
            sMessage.setCount(new BigDecimal(i));
            sMessage.setLists(Lists.newArrayList());
            sMessage.setHeaders(Maps.newHashMap());
            iDelayedQueue.addDelayedQueue(sMessage, (long) i, TimeUnit.SECONDS, DelayTaskEnum.ORDER_CONFIRM_TIME_OUT.getName());
        }
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
