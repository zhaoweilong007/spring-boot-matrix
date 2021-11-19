package com.zwl;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 账户服务
 *
 * @author ZhaoWeiLong
 * @since 2021/11/12
 */
@SpringBootApplication
@EnableAutoDataSourceProxy
@EnableDubbo(scanBasePackages = "com.zwl")
@EnableTransactionManagement
public class AccountApp {
  public static void main(String[] args) {
    SpringApplication.run(AccountApp.class, args);
  }
}
