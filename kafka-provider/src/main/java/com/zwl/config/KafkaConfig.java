package com.zwl.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
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
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * @author ZhaoWeiLong
 * @since 2021/12/1
 **/
@Configuration
@EnableKafka
public class KafkaConfig {


  @Bean
  public NewTopics topic() {
    return new NewTopics(TopicBuilder.name("test-topic-1").build(),
        TopicBuilder.name("test-topic-2").build(),
        TopicBuilder.name("test-topic-3").build());
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate(
      ProducerFactory<String, Object> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public ProducerFactory<String, Object> producerFactory(Map<String, Object> producerConfigs) {
    return new DefaultKafkaProducerFactory<>(producerConfigs);
  }

  @Bean
  public Map<String, Object> producerConfigs(ProducerFactory<Object, Object> pf) {
    Map<String, Object> props = new HashMap<>(pf.getConfigurationProperties());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return props;
  }


  @Bean
  public ReplyingKafkaTemplate<String, String, String> replyingTemplate(
      ProducerFactory<String, String> producerFactory,
      ConcurrentMessageListenerContainer<String, String> repliesContainer) {
    return new ReplyingKafkaTemplate<>(producerFactory, repliesContainer);
  }

  @Bean
  public ConcurrentMessageListenerContainer<String, String> repliesContainer(
      ConcurrentKafkaListenerContainerFactory<String, String> containerFactory) {
    ConcurrentMessageListenerContainer<String, String> repliesContainer = containerFactory.createContainer(
        "kReplies");
    repliesContainer.getContainerProperties().setGroupId("repliesGroup");
    repliesContainer.setAutoStartup(false);
    return repliesContainer;
  }

  @Bean
  public RoutingKafkaTemplate routingTemplate(GenericApplicationContext context,
      ProducerFactory<Object, Object> producerFactory) {

    // Clone the PF with a different Serializer, register with Spring for shutdown
    Map<String, Object> configs = new HashMap<>(producerFactory.getConfigurationProperties());
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
    DefaultKafkaProducerFactory<Object, Object> bytesPF = new DefaultKafkaProducerFactory<>(
        configs);
    context.registerBean(DefaultKafkaProducerFactory.class, "bytesPF", bytesPF);

    Map<Pattern, ProducerFactory<Object, Object>> map = new LinkedHashMap<>();
    map.put(Pattern.compile("test-topic-\\d"), producerFactory);
    map.put(Pattern.compile(".+"), bytesPF);
    return new RoutingKafkaTemplate(map);
  }


}
