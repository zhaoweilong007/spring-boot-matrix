package com.zwl.provider;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.zwl.config.RabbitConfig;
import com.zwl.model.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/22
 */
@Component
@Slf4j
public class ProviderRunner implements ApplicationRunner {

  @Autowired RabbitTemplate rabbitTemplate;

  @Autowired AsyncRabbitTemplate asyncRabbitTemplate;

  Snowflake snowflake = new Snowflake(1, 1, true);

  final ListenableFutureCallback<Object> callback =
      new ListenableFutureCallback<Object>() {
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
  public void run(ApplicationArguments args) throws Exception {
    final List<DemoMessage> list =
        Stream.generate(
                () -> {
                  final DemoMessage message = new DemoMessage();
                  message.setId(snowflake.nextId());
                  return message;
                })
            .limit(RandomUtil.randomInt(1000, 10000))
            .collect(Collectors.toList());

    list.forEach(
        demoMessage -> {
          Object receive = null;
          if (RandomUtil.randomInt(0, 10) % 2 == 1) {
            demoMessage.setDesc("同步消息");
            receive =
                rabbitTemplate.convertSendAndReceive(
                    RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, demoMessage);
            log.info("返回响应消息:{}", receive);
          } else {
            demoMessage.setDesc("异步消息");
            final AsyncRabbitTemplate.RabbitConverterFuture<Object> converterFuture =
                asyncRabbitTemplate.convertSendAndReceive(RabbitConfig.QUEUE_NAME, demoMessage);
            converterFuture.addCallback(callback);
          }
        });

    log.info("============消息发送完成===========");
  }
}
