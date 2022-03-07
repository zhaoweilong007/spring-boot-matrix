package com.zwl.consumer;

import com.zwl.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/7 10:54
 **/
@Component
@Slf4j
public class TestConsumer implements StreamListener<String, ObjectRecord<String, Message>> {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void onMessage(ObjectRecord<String, Message> message) {
        log.info("receiver message:{}", message);

    }
}
