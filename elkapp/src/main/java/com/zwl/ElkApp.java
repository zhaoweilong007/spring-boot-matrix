package com.zwl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 描述：
 *
 * @author zwl
 * @since ${DATE} ${TIME}
 */
@SpringBootApplication
@Slf4j
@EnableScheduling
public class ElkApp {
  public static void main(String[] args) {
    SpringApplication.run(ElkApp.class, args);
  }

  @Scheduled(fixedRate = 1000)
  public void test() {
    log.info(Thread.currentThread().getName() + ": " + System.currentTimeMillis());
  }
}
