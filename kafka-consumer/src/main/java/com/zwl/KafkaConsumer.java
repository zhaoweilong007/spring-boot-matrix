package com.zwl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/30
 **/
@SpringBootApplication
public class KafkaConsumer {

  public static void main(String[] args) {
    SpringApplication.run(KafkaConsumer.class, args);
  }

}
