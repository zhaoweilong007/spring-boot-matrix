package com.zwl.producer;

import cn.hutool.core.lang.Snowflake;
import com.zwl.config.KafkaConfig;
import com.zwl.model.DemoMessage;
import java.time.Instant;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author ZhaoWeiLong
 * @since 2021/12/1
 **/
@ConditionalOnBean(KafkaConfig.class)
@Component
public class kafkaRunner implements CommandLineRunner {

  /**
   * kafkaTemplate
   */
  @Autowired
  KafkaTemplate<String, Object> kafkaTemplate;

  /**
   * 支持发送/应答的template
   */
  @Autowired
  ReplyingKafkaTemplate<String, Object, String> replyingKafkaTemplate;

  /**
   * 一个基于主题名称路由消息的KafkaTemplate
   */
  @Autowired
  RoutingKafkaTemplate routingKafkaTemplate;


  Snowflake snowflake = new Snowflake(1, 1, true);


  @Override
  public void run(String... args) throws Exception {

    final DemoMessage demoMessage = new DemoMessage();
    demoMessage.setId(snowflake.nextId());
    demoMessage.setDesc("test message1");
    kafkaTemplate.send("test-topic-1", demoMessage);

    demoMessage.setDesc("test message2");
    kafkaTemplate.send("test-topic-1", demoMessage.getId().toString(), demoMessage);

    demoMessage.setDesc("test message3");
    kafkaTemplate.send("test-topic-1", 0, demoMessage.getId().toString(), demoMessage);

    demoMessage.setDesc("test message4");
    kafkaTemplate.send("test-topic-1", 0, Instant.now().toEpochMilli(),
        demoMessage.getId().toString(), demoMessage);

    demoMessage.setDesc("test message5");
    final ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(
        "test-topic-1", demoMessage);
    kafkaTemplate.send(producerRecord);

    demoMessage.setDesc("test message6");
    kafkaTemplate.send(MessageBuilder.withPayload(demoMessage).build());
  }
}
