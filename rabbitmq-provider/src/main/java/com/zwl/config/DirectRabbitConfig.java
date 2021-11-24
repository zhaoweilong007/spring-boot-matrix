package com.zwl.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 根据routeKey路由到binding的队列
 *
 * @author ZhaoWeiLong
 * @since 2021/11/22
 */
@Configuration
public class DirectRabbitConfig {

  public static final String QUEUE_NAME = "direct_queue";

  public static final String EXCHANGE_NAME = "direct_exchange_demo";

  public static final String ROUTING_KEY = "direct.demo";

  @Bean
  public Queue demoQueue() {
    return new Queue(QUEUE_NAME, true, false, false);
  }

  @Bean
  public DirectExchange directExchange() {
    return new DirectExchange(EXCHANGE_NAME, true, false);
  }

  @Bean
  public Binding binding(Queue demoQueue, DirectExchange directExchange) {
    return BindingBuilder.bind(demoQueue).to(directExchange).with(ROUTING_KEY);
  }
}
