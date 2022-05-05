package com.zwl.consumer;

import com.zwl.model.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author ZhaoWeiLong
 * @since 2021/12/3
 */
@Component
@Slf4j
public class KafkaConsumer {

  public static final String GROUP_PREFIX = "kafkaConsumer-group-";

  @Value("${spring.application.name}")
  private String appname;

  @KafkaListener(
      topics = {"test-topic-1"},
      groupId = GROUP_PREFIX + "test-topic-A")
  public void receiverTestTopic1A(DemoMessage demoMessage) {
    log.info(
        "invoke receiverTestTopic1A threadID:{},msg:{}",
        Thread.currentThread().getId(),
        demoMessage);
  }

  @KafkaListener(
      topics = {"test-topic-1"},
      groupId = GROUP_PREFIX + "test-topic-B")
  public void receiverTestTopic1B(DemoMessage demoMessage) {
    log.info(
        "invoke receiverTestTopic1B threadID:{},msg:{}",
        Thread.currentThread().getId(),
        demoMessage);
  }
}
