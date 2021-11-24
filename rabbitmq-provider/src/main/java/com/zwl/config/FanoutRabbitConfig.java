package com.zwl.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/24
 */
@Configuration
public class FanoutRabbitConfig {

  public static final String FANOUT_QUEUE_A = "fanout,queue.a";
  public static final String FANOUT_QUEUE_B = "fanout.queue.b";
  public static final String FANOUT_EXCHANGE = "fanout.exchange";

  @Bean
  public Queue fanoutQueueA() {
    return new Queue(FANOUT_QUEUE_A, true, false, false);
  }

  @Bean
  public Queue fanoutQueueB() {
    return new Queue(FANOUT_QUEUE_B, true, false, false);
  }

  @Bean
  public FanoutExchange fanoutExchange() {
    return new FanoutExchange(FANOUT_EXCHANGE, true, false);
  }

  @Bean
  public Binding fanoutBidingA(Queue fanoutQueueA, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(fanoutQueueA).to(fanoutExchange);
  }

  @Bean
  public Binding fanoutBidingB(Queue fanoutQueueB, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(fanoutQueueB).to(fanoutExchange);
  }
}
