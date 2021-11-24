package com.zwl.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/22
 */
@Configuration
public class RabbitConfig {

  /** queue匹配 */
  public static final String QUEUE_NAME = "direct_queue";
  public static final String EXCHANGE_NAME = "direct_exchange_demo";
  public static final String ROUTING_KEY = "direct.demo";

  /** topic配置 */
  public static final String TOPIC_QUEUE_FIRST = "TOPIC_QUEUE_FIRST";
  public static final String TOPIC_QUEUE_SECOND = "TOPIC_QUEUE_SECOND";
  public static final String TOPIC_EXCHANGE = "topic.exchange";

  /** fanout匹配 */
  public static final String FANOUT_QUEUE_A = "fanout,queue.a";
  public static final String FANOUT_QUEUE_B = "fanout.queue.b";
  public static final String FANOUT_EXCHANGE = "fanout.exchange";

  public static final String HEADER_EXCHANGE = "header.exchange";
  public static final String HEADER_QUEUE_A = "HEADER_QUEUE_A";
  public static final String HEADER_QUEUE_B = "HEADER_QUEUE_B";

  @Bean
  public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(
      ConnectionFactory connectionFactory) {
    final SimpleRabbitListenerContainerFactory containerFactory =
        new SimpleRabbitListenerContainerFactory();
    //消息转换
    containerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
    //连接工厂
    containerFactory.setConnectionFactory(connectionFactory);
    //设置手动ack模式
    containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
    return containerFactory;
  }
}
