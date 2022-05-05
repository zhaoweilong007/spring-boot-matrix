package com.zwl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/15
 */
@Configuration
public class MongoConfig {

  @Bean
  public MongoTransactionManager mongoTransactionManager(
      MongoDatabaseFactory mongoDatabaseFactory) {
    return new MongoTransactionManager(mongoDatabaseFactory);
  }
}
