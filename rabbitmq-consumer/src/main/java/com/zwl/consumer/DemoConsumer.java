package com.zwl.consumer;

import com.zwl.config.RabbitConfig;
import com.zwl.model.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/22
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_NAME,concurrency ="100" )
@Slf4j
public class DemoConsumer {

  @RabbitHandler
  public String consumer(DemoMessage demoMessage) {
    log.info("开始消费消息:{},msg:{}", Thread.currentThread().getName(), demoMessage);
    return "ok";
  }
}
