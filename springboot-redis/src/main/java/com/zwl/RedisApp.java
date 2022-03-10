package com.zwl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.zwl.model.LMessage;
import com.zwl.model.SMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/4 17:45
 **/
@SpringBootApplication
@EnableScheduling
@Slf4j
public class RedisApp {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RedisApp.class, args);
    }


    @Scheduled(fixedRate = 1000 * 20)
    public void sendMessage() {
        log.info(">>>>>>>>>>>>>>>>>>>start send message<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        StreamOperations<String, Object, Object> opsForStream = redisTemplate.opsForStream();
        for (int i = 0; i < 5; i++) {
            ArrayList<String> list = Stream.generate(() -> RandomUtil.randomString(5)).limit(10).collect(Collectors.toCollection(ArrayList::new));

            SMessage message = new SMessage()
                    .setSenderId((long) i).
                    setReceiverId(RandomUtil.randomLong()).
                    setContent(RandomUtil.randomString(10))
                    .setCount(new BigDecimal("10.1222"))
                    .setLists(list)
                    .addHeader("headerKey", "val");
            ObjectRecord<String, SMessage> objectRecord = StreamRecords.newRecord()
                    .ofObject(message)
                    .withStreamKey(message.getStreamKey())
                    .withId(RecordId.autoGenerate());
            RecordId recordId = opsForStream.add(objectRecord);

            LMessage lMessage = new LMessage()
                    .setName("msg=" + i)
                    .setLists(list)
                    .setCount(BigDecimal.valueOf(RandomUtil.randomDouble(2, RoundingMode.HALF_UP)));
            log.info("send lmessage:{}", JSON.toJSONString(lMessage, true));
            redisTemplate.convertAndSend(lMessage.getChannel(), lMessage);

//            log.info("send message recordId:{} message:{}", recordId, message);
        }
    }
}
