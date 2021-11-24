package com.zwl.provider;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.zwl.config.DirectRabbitConfig;
import com.zwl.config.FanoutRabbitConfig;
import com.zwl.config.TopicRabbitConfig;
import com.zwl.config.headerRabbitConfig;
import com.zwl.model.DemoMessage;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/22
 */
@Component
@Slf4j
public class ProviderRunner implements ApplicationRunner {

  @Autowired RabbitTemplate rabbitTemplate;

  @Autowired AsyncRabbitTemplate asyncRabbitTemplate;

  @Autowired MessageConverter messageConverter;

  Snowflake snowflake = new Snowflake(1, 1, true);

  final ListenableFutureCallback<Object> callback =
      new ListenableFutureCallback<>() {
        @Override
        public void onFailure(Throwable ex) {
          log.info("消息处理异常：{}", ex.getMessage());
        }

        @Override
        public void onSuccess(Object result) {
          log.info("消息处理成功：{}", result);
        }
      };

  @Override
  public void run(ApplicationArguments args) {
    final List<DemoMessage> list =
        Stream.generate(
                () -> {
                  final DemoMessage message = new DemoMessage();
                  message.setId(snowflake.nextId());
                  return message;
                })
            .limit(RandomUtil.randomInt(100, 200))
            .collect(Collectors.toList());

    directMode(list);
    log.info("============direct消息发送完成===========");

    topicMode(list);
    log.info("============topic消息发送完成===========");

    fanoutMode(list);
    log.info("============fanout消息发送完成===========");

    headerMode(list);
    log.info("=============header消息发送完成===========");
  }

  private void headerMode(List<DemoMessage> list) {
    list.forEach(
        demoMessage -> {
          MessageProperties messageProperties = new MessageProperties();
          if (demoMessage.getId() % 2 == 1) {
            demoMessage.setDesc("消息:system:dev,level:info");
            rabbitTemplate.convertAndSend(
                headerRabbitConfig.HEADER_EXCHANGE,
                null,
                demoMessage,
                message -> {
                  message.getMessageProperties().setHeader("system", "dev");
                  message.getMessageProperties().setHeader("level", "info");
                  return message;
                });
          } else {
            demoMessage.setDesc("消息:level:warn");
            rabbitTemplate.convertAndSend(
                headerRabbitConfig.HEADER_EXCHANGE,
                null,
                demoMessage,
                message -> {
                  message.getMessageProperties().setHeader("level", "warn");
                  return message;
                });
          }
        });
  }

  private void fanoutMode(List<DemoMessage> list) {
    list.forEach(
        demoMessage -> {
          demoMessage.setDesc("fanout msg");
          rabbitTemplate.convertAndSend(FanoutRabbitConfig.FANOUT_EXCHANGE, demoMessage);
        });
  }

  private void topicMode(List<DemoMessage> list) {
    list.forEach(
        demoMessage -> {
          if (demoMessage.getId() % 2 == 1) {
            demoMessage.setDesc("hello.msg.topic");
            rabbitTemplate.convertAndSend(
                TopicRabbitConfig.TOPIC_EXCHANGE, "hello.msg.topic", demoMessage);
          } else {
            demoMessage.setDesc("msg.topic");
            rabbitTemplate.convertAndSend(
                TopicRabbitConfig.TOPIC_EXCHANGE, "msg.topic", demoMessage);
          }
        });
  }

  private void directMode(List<DemoMessage> list) {
    list.forEach(
        demoMessage -> {
          if (RandomUtil.randomInt(0, 10) % 2 == 1) {
            demoMessage.setDesc("direct同步消息");
            rabbitTemplate.convertSendAndReceive(
                DirectRabbitConfig.EXCHANGE_NAME, DirectRabbitConfig.ROUTING_KEY, demoMessage);
          } else {
            demoMessage.setDesc("direct异步消息");
            final AsyncRabbitTemplate.RabbitConverterFuture<Object> converterFuture =
                asyncRabbitTemplate.convertSendAndReceive(
                    DirectRabbitConfig.QUEUE_NAME, demoMessage);
            converterFuture.addCallback(callback);
          }
        });
  }
}
