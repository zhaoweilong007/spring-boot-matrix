package com.zwl.config;

import com.zwl.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/7 13:34
 **/
@Slf4j
public class ServletInitListener implements ServletContextListener {

    @Autowired
    StreamMessageListenerContainer<String, ObjectRecord<String, Message>> listenerContainer;

    @Autowired
    Subscription subscription;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("servlet init....");
        listenerContainer.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("servlet destroy...");
        listenerContainer.remove(subscription);
        listenerContainer.stop();
    }
}
