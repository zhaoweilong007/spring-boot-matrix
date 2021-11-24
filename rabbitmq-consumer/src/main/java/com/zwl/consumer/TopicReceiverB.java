package com.zwl.consumer;

import com.rabbitmq.client.Channel;
import com.zwl.config.RabbitConfig;
import com.zwl.model.DemoMessage;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/23
 */
@Component
@RabbitListener(queues = {RabbitConfig.TOPIC_QUEUE_SECOND})
@Slf4j
public class TopicReceiverB {

  @RabbitHandler
  public void receiver(DemoMessage demoMessage, Channel channel, Message message)
      throws IOException {
    log.info("TopicReceiverB-线程:{} msg:{}", Thread.currentThread().getName(), demoMessage);
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
  }
}
