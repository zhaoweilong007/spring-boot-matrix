package com.zwl.producer;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.zwl.model.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/25
 */
@Component
@Slf4j
public class producerRunner implements CommandLineRunner {

  @Autowired RocketMQTemplate rocketMQTemplate;

  Snowflake snowflake = new Snowflake(1, 1, true);
  /** 发送同步消息 */
  Consumer<List<DemoMessage>> sendSyncMsg =
      new Consumer<List<DemoMessage>>() {
        @Override
        public void accept(List<DemoMessage> list) {
          list.forEach(
              demoMessage -> {
                demoMessage.setDesc("同步消息");
                rocketMQTemplate.convertAndSend("test-topic-1", demoMessage);
              });
        }
      };
  /** 发送异步消息 */
  Consumer<List<DemoMessage>> sendAsyncMsg =
      new Consumer<List<DemoMessage>>() {
        @Override
        public void accept(List<DemoMessage> list) {
          list.forEach(
              demoMessage -> {
                demoMessage.setDesc("异步消息");
                rocketMQTemplate.asyncSend(
                    "test-topic-2",
                    MessageBuilder.withPayload(demoMessage).build(),
                    new SendCallback() {
                      @Override
                      public void onSuccess(SendResult sendResult) {
                        log.info("异步消息发送成功:{}", sendResult);
                      }

                      @Override
                      public void onException(Throwable e) {
                        log.info("异步消息发送失败:{}", ExceptionUtil.stacktraceToString(e));
                      }
                    });
              });
        }
      };
  /** 发送顺序消息 */
  Consumer<List<DemoMessage>> sendOrderlyMsg =
      new Consumer<List<DemoMessage>>() {
        AtomicInteger count = new AtomicInteger(0);

        @Override
        public void accept(List<DemoMessage> list) {
          list.forEach(
              demoMessage -> {
                demoMessage.setDesc("顺序消息" + count.incrementAndGet());
                rocketMQTemplate.syncSendOrderly(
                    "test-topic-3",
                    MessageBuilder.withPayload(demoMessage).build(),
                    demoMessage.getId().toString());
              });
        }
      };
  /** 发送批量消息 */
  Consumer<List<DemoMessage>> sendBatchMsg =
      new Consumer<List<DemoMessage>>() {
        @Override
        public void accept(List<DemoMessage> list) {
          list.forEach(
              demoMessage -> {
                demoMessage.setDesc("批量消息" + System.currentTimeMillis());
              });
          rocketMQTemplate.convertAndSend("test-topic-4", list);
        }
      };

  Consumer<List<DemoMessage>> sendTransactionMsg =
      new Consumer<List<DemoMessage>>() {
        @Override
        public void accept(List<DemoMessage> list) {
          list.forEach(
              demoMessage -> {
                demoMessage.setDesc("事务消息" + System.currentTimeMillis());
                rocketMQTemplate.sendMessageInTransaction(
                    "test-topic-5", MessageBuilder.withPayload(demoMessage).build(), null);
              });
        }
      };

  @Override
  public void run(String... args) throws Exception {
    final List<DemoMessage> list =
        Stream.generate(
                () -> {
                  final DemoMessage message = new DemoMessage();
                  message.setId(snowflake.nextId());
                  return message;
                })
            .limit(RandomUtil.randomInt(10, 100))
            .collect(Collectors.toList());

    sendSyncMsg.accept(list);

    sendOrderlyMsg.accept(list);

    sendBatchMsg.accept(list);

    sendTransactionMsg.accept(list);

    sendAsyncMsg.accept(list);

    log.info("=============消息全部发送完成===================");
  }
}
