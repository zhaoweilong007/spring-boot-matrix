package com.zwl.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author ZhaoWeiLong
 * @since 2021/12/1
 */
@Configuration
@EnableKafka
public class KafkaConfig {

  @Bean
  public NewTopics topic() {
    return new NewTopics(
        TopicBuilder.name("test-topic-1").build(),
        TopicBuilder.name("test-topic-2").build(),
        TopicBuilder.name("test-topic-3").build());
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> pf) {
    return new KafkaTemplate<String, Object>(pf);
  }

  @Bean
  public ReplyingKafkaTemplate<String, Object, Object> replyingTemplate(
      ProducerFactory<String, Object> pf,
      ConcurrentMessageListenerContainer<String, Object> repliesContainer) {
    return new ReplyingKafkaTemplate<String, Object, Object>(pf, repliesContainer);
  }

  @Bean
  public ConcurrentMessageListenerContainer<String, Object> repliesContainer(
      ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory) {
    ConcurrentMessageListenerContainer<String, Object> repliesContainer =
        containerFactory.createContainer("replies");
    repliesContainer.getContainerProperties().setGroupId("repliesGroup");
    repliesContainer.setAutoStartup(false);
    return repliesContainer;
  }

  @Bean
  public RoutingKafkaTemplate routingTemplate(
      GenericApplicationContext context, ProducerFactory<Object, Object> producerFactory) {

    // Clone the PF with a different Serializer, register with Spring for shutdown
    Map<String, Object> configs = new HashMap<>(producerFactory.getConfigurationProperties());
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
    DefaultKafkaProducerFactory<Object, Object> bytesPF =
        new DefaultKafkaProducerFactory<>(configs);
    context.registerBean(DefaultKafkaProducerFactory.class, "bytesPF", bytesPF);

    Map<Pattern, ProducerFactory<Object, Object>> map = new LinkedHashMap<>();
    map.put(Pattern.compile("test-topic-\\d"), producerFactory);
    map.put(Pattern.compile(".+"), bytesPF);
    return new RoutingKafkaTemplate(map);
  }
}
