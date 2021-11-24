package com.zwl.consumer;

import com.zwl.config.RabbitConfig;
import com.zwl.model.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/24
 */
@RabbitListener(queues = {RabbitConfig.FANOUT_QUEUE_A})
@Slf4j
public class FanoutReceiverA {

  @RabbitHandler
  public void handler(DemoMessage demoMessage) {
    log.info("FanoutReceiverA handler :{}", demoMessage);
  }
}
