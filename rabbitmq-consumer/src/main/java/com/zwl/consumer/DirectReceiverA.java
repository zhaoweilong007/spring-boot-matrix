package com.zwl.consumer;

import com.rabbitmq.client.Channel;
import com.zwl.config.RabbitConfig;
import com.zwl.model.DemoMessage;
import java.io.IOException;
import javax.swing.event.ChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * DirectReceiverA和DirectReceiverB竞争消费，不会重复消费，A和B并发线程数为一百
 *
 * @author ZhaoWeiLong
 * @since 2021/11/22
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_NAME, concurrency = "100")
@Slf4j
public class DirectReceiverA {

  @RabbitHandler
  public String consumer(DemoMessage demoMessage, Channel channel, Message message)
      throws IOException {
    try {
      log.info("DirectReceiverA-线程：{}消费消息:{}", Thread.currentThread().getName(), demoMessage);
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
