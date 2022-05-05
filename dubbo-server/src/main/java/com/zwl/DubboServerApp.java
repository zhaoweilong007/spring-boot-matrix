package com.zwl;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/18
 */
@SpringBootApplication
@EnableDubbo
public class DubboServerApp {

  public static void main(String[] args) {
    SpringApplication.run(DubboServerApp.class, args);
  }
}
