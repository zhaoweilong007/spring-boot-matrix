package com.zwl.config;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zwl.consumer.TestConsumerA;
import com.zwl.consumer.TestConsumerB;
import com.zwl.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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

    private final TestConsumerA testConsumerA;

    private final TestConsumerB testConsumerB;

    public static final String STREAMKEY = "queueA";
    public static final String groupName = "group-A";
    public static final String CONSUMER_A = "consumer-A";
    public static final String CONSUMER_B = "consumer-B";


    @Bean(destroyMethod = "stop")
    public StreamMessageListenerContainer<String, ObjectRecord<String, Message>> listenerContainer(RedisConnectionFactory factory) throws UnknownHostException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("redis-stream-consumer-%d").build();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

        //配置选项
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, Message>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .executor(threadPoolExecutor)
                .errorHandler(t -> log.error("消费异常：{}", ExceptionUtil.stacktraceToString(t)))
                .batchSize(20)
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(Message.class)
                .build();
        //创建监听容器
        StreamMessageListenerContainer<String, ObjectRecord<String, Message>> listenerContainer = StreamMessageListenerContainer.create(factory, options);

        //配置消费者
        listenerContainer.receiveAutoAck(Consumer.from(groupName, CONSUMER_A),
                StreamOffset.create(STREAMKEY, ReadOffset.lastConsumed()), testConsumerA);

        listenerContainer.receive(Consumer.from(groupName, CONSUMER_B),
                StreamOffset.create(STREAMKEY, ReadOffset.lastConsumed()), testConsumerB);

        listenerContainer.start();
        checkGroup();
        return listenerContainer;
    }

    private void checkGroup() {
        log.info("check group...");
        Stream<String> groupStream = Stream.of(RedisConfig.groupName);
        StreamInfo.XInfoGroups groups = stringRedisTemplate.opsForStream().groups(STREAMKEY);
        groupStream.filter(groupName -> groups.stream().noneMatch(s -> s.groupName().equals(groupName))).forEach(groupName -> {
            stringRedisTemplate.opsForStream().createGroup(STREAMKEY, ReadOffset.latest(), groupName);
        });
    }


    //    @Scheduled(fixedRate = 1000 * 10)
    public void sendMessage() {
        log.info(">>>>>>>>>>>>>>>>>>>start send message<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        StreamOperations<String, Object, Object> opsForStream = stringRedisTemplate.opsForStream();
        for (int i = 0; i < 20; i++) {
            Message message = Message.builder()
                    .senderId((long) i).receiverId(RandomUtil.randomLong()).content(RandomUtil.randomString(10))
                    .build();
            ObjectRecord<String, Message> record = StreamRecords.newRecord().ofObject(message).withStreamKey(STREAMKEY).withId(RecordId.autoGenerate());
            RecordId recordId = opsForStream.add(record);
//            log.info("send message recordId:{} message:{}", recordId, message);
        }
    }

}
