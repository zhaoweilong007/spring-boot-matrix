package com.zwl.consumer;

import com.rabbitmq.client.Channel;
import com.zwl.config.RabbitConfig;
import com.zwl.model.DemoMessage;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/24
 */
@Component
@Slf4j
public class headerReceiver {

  @RabbitListener(queues = {RabbitConfig.HEADER_QUEUE_A})
  public void handlerA(
      @Payload DemoMessage demoMessage,
      Channel channel,
      Message message,
      @Header("system") String system,
      @Header("level") String level)
      throws IOException {
    log.info(
        "headerReceiver handlerA msg:{},headers:{}",
        demoMessage,
        message.getMessageProperties().getHeaders());
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
  }

  @RabbitListener(queues = {RabbitConfig.HEADER_QUEUE_B})
  public void handlerB(DemoMessage demoMessage, Channel channel, Message message)
      throws IOException {
    log.info(
        "headerReceiver handlerB msg:{},headers:{}",
        demoMessage,
        message.getMessageProperties().getHeaders());
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
  }
}
