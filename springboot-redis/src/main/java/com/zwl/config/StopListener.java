package com.zwl.config;

import com.zwl.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/7 13:53
 **/
@Component
@Slf4j
public class StopListener implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    StreamMessageListenerContainer<String, ObjectRecord<String, Message>> listenerContainer;

    @Autowired
    Subscription subscription;


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("onApplicationEvent >>> ContextClosedEvent...");
        listenerContainer.remove(subscription);
        listenerContainer.stop();
    }
}
