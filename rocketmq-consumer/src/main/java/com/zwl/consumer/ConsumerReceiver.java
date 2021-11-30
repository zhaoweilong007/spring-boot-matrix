package com.zwl.consumer;

import com.alibaba.fastjson.JSON;
import com.zwl.model.DemoMessage;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/25
 */
@Slf4j
public class ConsumerReceiver {

  @RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "${rocketmq.consumer.group}")
  @Component
  public static class SyncConsumer implements RocketMQListener<DemoMessage> {

    @Override
    public void onMessage(DemoMessage message) {
      log.info("线程：{}，SyncConsumer-msg:{}", Thread.currentThread().getName(), message);
    }
  }

  @RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "${rocketmq.consumer.group}")
  @Component
  public static class AsyncConsumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
      log.info("线程：{}，AsyncConsumer-msg:{}", Thread.currentThread().getName(),
          JSON.parseObject(message.getBody(), DemoMessage.class));
    }
  }

  @RocketMQMessageListener(
      topic = "test-topic-3",
      consumerGroup = "${rocketmq.consumer.group}", consumeMode = ConsumeMode.ORDERLY, messageModel = MessageModel.CLUSTERING)
  @Component
  public static class OrderlyConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt message) {
      log.info("线程：{}，OrderlyConsumer-msg:{}", Thread.currentThread().getName(), message);
    }
  }

  @RocketMQMessageListener(topic = "test-topic-4", consumerGroup = "${rocketmq.consumer.group}")
  @Component
  public static class BatchConsumer implements RocketMQListener<List<DemoMessage>> {

    @Override
    public void onMessage(List<DemoMessage> messages) {
      log.info("线程：{}，BatchConsumer-msg:{}", Thread.currentThread().getName(), messages);
    }
  }

  @RocketMQMessageListener(topic = "test-topic-5", consumerGroup = "${rocketmq.consumer.group}")
  @Component
  public static class TransactionConsumer implements RocketMQListener<DemoMessage> {

    @Override
    public void onMessage(DemoMessage message) {
      log.info("线程：{}，TransactionConsumer-msg:{}", Thread.currentThread().getName(), message);
    }
  }

}
