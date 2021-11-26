package com.zwl.consumer;

import com.zwl.model.DemoMessage;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
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
  public static class AsyncConsumer implements RocketMQListener<DemoMessage> {

    @Override
    public void onMessage(DemoMessage message) {
      log.info("线程：{}，AsyncConsumer-msg:{}", Thread.currentThread().getName(), message);
    }
  }

  @RocketMQMessageListener(
      topic = "test-topic-3",
      consumerGroup = "${rocketmq.consumer.group}",
      consumeMode = ConsumeMode.ORDERLY)
  @Component
  public static class OrderlyConsumer implements RocketMQListener<DemoMessage> {

    @Override
    public void onMessage(DemoMessage message) {
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

  @RocketMQTransactionListener
  @Component
  public static class TransactionConsumer implements RocketMQLocalTransactionListener {

    /**
     * 执行本地事务
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
      log.info("invoke executeLocalTransaction msg:{}", msg);
      return RocketMQLocalTransactionState.UNKNOWN;
    }

    /**
     * 检车事务状态
     *
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
      log.info("invoke checkLocalTransaction msg:{}", msg);
      return RocketMQLocalTransactionState.COMMIT;
    }
  }
}
