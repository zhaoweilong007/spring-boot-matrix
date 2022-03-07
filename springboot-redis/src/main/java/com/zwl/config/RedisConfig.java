package com.zwl.config;

import cn.hutool.core.util.RandomUtil;
import com.zwl.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.stream.Stream;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/4 17:55
 **/
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@Slf4j
public class RedisConfig {

    private final StringRedisTemplate stringRedisTemplate;

    private final StreamListener<String, ObjectRecord<String, Message>> streamListener;

    public static final String STREAMKEY = "queueA";
    public static final String groupName = "group-A";


    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, Message>> listenerContainer(RedisConnectionFactory factory) {
        //配置选项
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, Message>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(Message.class)
                .build();
        //创建监听容器
        StreamMessageListenerContainer<String, ObjectRecord<String, Message>> container = StreamMessageListenerContainer.create(factory, options);
        return container;
    }


    @Bean
    public Subscription subscription(StreamMessageListenerContainer<String, ObjectRecord<String, Message>> listenerContainer) throws UnknownHostException {
        //配置消费者
        Subscription subscription = listenerContainer.receiveAutoAck(Consumer.from(groupName, InetAddress.getLocalHost().getHostName()),
                StreamOffset.create(STREAMKEY, ReadOffset.lastConsumed()), streamListener);
        //检车消费者是否存在 不存在则创建
        checkGroup();
        return subscription;
    }


    private void checkGroup() {
        Stream<String> groupStream = Stream.of(RedisConfig.groupName);
        StreamInfo.XInfoGroups groups = stringRedisTemplate.opsForStream().groups(STREAMKEY);
        groupStream.filter(groupName -> groups.stream().noneMatch(s -> s.groupName().equals(groupName))).forEach(groupName -> {
            stringRedisTemplate.opsForStream().createGroup(STREAMKEY, ReadOffset.latest(), groupName);
        });
    }


    @Scheduled(fixedRate = 1000L)
    public void sendMessage() {
        StreamOperations<String, Object, Object> opsForStream = stringRedisTemplate.opsForStream();
        Message message = Message.builder()
                .senderId(RandomUtil.randomLong()).receiverId(RandomUtil.randomLong()).content(RandomUtil.randomString(10)).build();
        ObjectRecord<String, Message> record = StreamRecords.newRecord().ofObject(message).withStreamKey(STREAMKEY);
        RecordId recordId = opsForStream.add(record);
        log.info("send message recordId:{} message:{}", recordId, message);
    }

}
