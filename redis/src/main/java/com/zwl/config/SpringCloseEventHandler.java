package com.zwl.config;

import com.zwl.stream.AbstractStreamListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/10 16:08
 **/
@Component
@Slf4j
public class SpringCloseEventHandler implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    RedisConnectionFactory factory;


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("close event invoke ...");
        factory.getConnection().close();
    }
}
