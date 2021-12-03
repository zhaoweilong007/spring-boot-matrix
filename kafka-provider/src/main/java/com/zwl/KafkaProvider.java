package com.zwl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/30
 **/
@SpringBootApplication
@EnableTransactionManagement
public class KafkaProvider {

  public static void main(String[] args) {
    SpringApplication.run(KafkaProvider.class, args);
  }

}
