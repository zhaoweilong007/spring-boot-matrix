package com.zwl.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/22
 */
@Configuration
public class RabbitConfig {

  public static final String QUEUE_NAME = "demo_queue_01";

  public static final String EXCHANGE_NAME = "exchange_demo_01";

  public static final String ROUTING_KEY = "routing_key_01";

  @Bean
  public Queue demoQueue() {
    return new Queue(QUEUE_NAME, true, false, false);
  }

  @Bean
  public Exchange demoExchange() {
    return new DirectExchange(EXCHANGE_NAME, true, false);
  }

  @Bean
  public Binding binding() {
    return BindingBuilder.bind(demoQueue()).to(demoExchange()).with(ROUTING_KEY).noargs();
  }

  @Bean
  public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
    return new AsyncRabbitTemplate(rabbitTemplate);
  }
}
