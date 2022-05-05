package com.zwl.config;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.system.SystemUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zwl.pubsub.AbstractChannelListener;
import com.zwl.stream.AbstractStreamListener;
import com.zwl.stream.AbstractStreamMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.string());
        return redisTemplate;
    }

    /**
     * 配置pub/sub
     *
     * @param factory   连接工厂
     * @param listeners 监听器
     * @return 监听容器
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory factory, List<AbstractChannelListener<?>> listeners) {
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(factory);
        listenerContainer.setErrorHandler(t -> {
            log.error("redis listener 处理异常:{}", ExceptionUtil.stacktraceToString(t));
        });
        listeners.forEach(listener -> {
            listenerContainer.addMessageListener(listener, listener.getTopic());
        });
        return listenerContainer;
    }


    /**
     * redis stream 配置
     *
     * @param factory       链接工厂
     * @param listeners     监听器
     * @param redisTemplate redis
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public StreamMessageListenerContainer<String, ObjectRecord<String, AbstractStreamMessage>> listenerContainer(RedisConnectionFactory factory, RedisTemplate<String, Object> redisTemplate, List<AbstractStreamListener> listeners) {
        //配置线程池
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("redis-stream-consumer-%d").build();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

        //配置选项
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, AbstractStreamMessage>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .executor(threadPoolExecutor)
                .errorHandler(t -> log.error("消费异常：{}", ExceptionUtil.stacktraceToString(t)))
                .batchSize(20)
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(AbstractStreamMessage.class)
                .build();
        //创建监听容器
        StreamMessageListenerContainer<String, ObjectRecord<String, AbstractStreamMessage>> listenerContainer = StreamMessageListenerContainer.<String, ObjectRecord<String, AbstractStreamMessage>>create(factory, options);

        //消费者名称
        String consumerName = SystemUtil.getHostInfo().getAddress() + "@" + SystemUtil.getCurrentPID();

        java.util.function.Consumer<AbstractStreamListener<?>> checkGroup = checkGroup(redisTemplate);

        listeners.forEach(listener -> {
            //检车消费组组是否存在 不存在则创建消费组
            checkGroup.accept(listener);
            //创建消费者
            Consumer consumer = Consumer.from(listener.getGroup(), consumerName);
            //指定消费位置
            StreamOffset<String> streamOffset = StreamOffset.create(listener.getStreamKey(), ReadOffset.lastConsumed());
            StreamMessageListenerContainer.ConsumerStreamReadRequest<String> streamReadRequest = StreamMessageListenerContainer.StreamReadRequest.builder(streamOffset).consumer(consumer)
                    .autoAcknowledge(false) //不自动ack
                    .cancelOnError(throwable -> false)//在收到异常是否取消消费
                    .build();
            listener.setRedisTemplate(redisTemplate);
            //注册监听器
            listenerContainer.register(streamReadRequest, listener);
        });
        return listenerContainer;
    }

    /**
     * 检查消费组是否存在
     */
    private java.util.function.Consumer<AbstractStreamListener<?>> checkGroup(RedisTemplate<String, Object> redisTemplate) {
        StreamOperations<String, Object, Object> forStream = redisTemplate.opsForStream();
        return listener -> {
            try {
                StreamInfo.XInfoGroups infoGroups = forStream.groups(listener.getStreamKey());
                if (infoGroups.isEmpty() || infoGroups.stream().noneMatch(xInfoGroup -> xInfoGroup.groupName().equals(listener.getGroup()))) {
                    //创建消费者组
                    forStream.createGroup(listener.getStreamKey(), listener.getGroup());
                }
            } catch (Exception ignore) {
                forStream.createGroup(listener.getStreamKey(), listener.getGroup());
            }
        };
    }


}
