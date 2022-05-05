package com.zwl;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/22
 */
@SpringBootApplication
@EnableAsync
@EnableRabbit
@EnableScheduling
public class RabbitmqProvider {
  public static void main(String[] args) {
    SpringApplication.run(RabbitmqProvider.class, args);
  }
}
