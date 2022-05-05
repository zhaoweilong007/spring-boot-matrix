package com.zwl.consumer;

import com.rabbitmq.client.Channel;
import com.zwl.config.RabbitConfig;
import com.zwl.model.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/22
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_NAME, concurrency = "100")
@Slf4j
public class DirectReceiverB {

  @RabbitHandler
  public String consumer(DemoMessage demoMessage, Channel channel, Message message)
      throws IOException {
    try {
      log.info("DirectReceiverB-线程：{}消费消息:{}", Thread.currentThread().getName(), demoMessage);
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
      return "ok";
    } catch (Exception e) {
      e.printStackTrace();
      log.info("处理失败：{}", e.getMessage());
      channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
      return "err";
    }
  }
}
