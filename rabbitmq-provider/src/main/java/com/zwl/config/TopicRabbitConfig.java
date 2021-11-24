package com.zwl.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/23
 */
@Configuration
public class TopicRabbitConfig {

  public static final String TOPIC_QUEUE_FIRST = "TOPIC_QUEUE_FIRST";
  public static final String TOPIC_QUEUE_SECOND = "TOPIC_QUEUE_SECOND";
  public static final String TOPIC_EXCHANGE = "topic.exchange";

  @Bean
  public Queue firstQueue() {
    return new Queue(TOPIC_QUEUE_FIRST, true, false, false);
  }

  @Bean
  public Queue secondQueue() {
    return new Queue(TOPIC_QUEUE_SECOND, true, false, false);
  }

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(TOPIC_EXCHANGE, true, false);
  }

  @Bean
  public Binding bindingFirst(Queue firstQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(firstQueue).to(topicExchange).with("*.msg.#");
  }

  @Bean
  public Binding bindingSecond(Queue secondQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(secondQueue).to(topicExchange).with("msg.#");
  }
}
