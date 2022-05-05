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
 * @since 2021/11/23
 */
@Component
@RabbitListener(queues = {RabbitConfig.TOPIC_QUEUE_FIRST})
@Slf4j
public class TopicReceiverA {

  @RabbitHandler
  public void receiver(DemoMessage demoMessage, Channel channel, Message message)
      throws IOException {
    log.info("TopicReceiverA-线程:{} msg:{}", Thread.currentThread().getName(), demoMessage);
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
  }
}
