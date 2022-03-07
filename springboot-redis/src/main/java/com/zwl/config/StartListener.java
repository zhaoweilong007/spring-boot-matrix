package com.zwl.config;

import com.zwl.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.stereotype.Component;

/**
 * 描述：spring启动
 *
 * @author zwl
 * @since 2022/3/7 13:52
 **/
@Component
@Slf4j
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    StreamMessageListenerContainer<String, ObjectRecord<String, Message>> listenerContainer;

    @Autowired
    Subscription subscription;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("start StreamMessageListenerContainer...");
        listenerContainer.start();
    }
}
