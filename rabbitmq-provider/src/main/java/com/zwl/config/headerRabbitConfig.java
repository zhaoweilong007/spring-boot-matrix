package com.zwl.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/24
 */
@Configuration
public class headerRabbitConfig {

  public static final String HEADER_EXCHANGE = "header.exchange";
  public static final String HEADER_QUEUE_A = "HEADER_QUEUE_A";
  public static final String HEADER_QUEUE_B = "HEADER_QUEUE_B";

  @Bean
  public HeadersExchange headersExchange() {
    return new HeadersExchange(HEADER_EXCHANGE, true, false);
  }

  @Bean
  public Queue headerQueueA() {
    return new Queue(HEADER_QUEUE_A, true, false, false);
  }

  @Bean
  public Queue headerQueueB() {
    return new Queue(HEADER_QUEUE_B, true, false, false);
  }

  @Bean
  public Binding headerBidingA(Queue headerQueueA, HeadersExchange headersExchange) {
    return BindingBuilder.bind(headerQueueA)
        .to(headersExchange)
        .whereAll(
            new HashMap<String, Object>() {
              {
                put("system", "dev");
                put("level", "info");
              }
            })
        .match();
  }

  @Bean
  Binding headerBidingB(Queue headerQueueB, HeadersExchange headersExchange) {
    return BindingBuilder.bind(headerQueueB)
        .to(headersExchange)
        .whereAny(
            new HashMap<String, Object>() {
              {
                put("system", "prod");
                put("level", "warn");
              }
            })
        .match();
  }
}
