package com.zwl.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/23
 */
@Configuration
@Slf4j
public class RabbitMQConfig {

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMandatory(true);
    rabbitTemplate.setMessageConverter(messageConverter);
    rabbitTemplate.setConfirmCallback(
        (correlationData, ack, cause) -> {
          log.info("确认消息 correlationData：{}", JSON.toJSONString(correlationData));
          log.info("确认消息 ack ：{}", ack);
          log.info("确认消息失败原因 ：{}", cause);
        });
    rabbitTemplate.setReturnsCallback(
        returned -> log.info("ReturnsCallback :{}", JSON.toJSONString(returned)));
    return rabbitTemplate;
  }

  @Bean
  public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
    return new AsyncRabbitTemplate(rabbitTemplate);
  }
}
