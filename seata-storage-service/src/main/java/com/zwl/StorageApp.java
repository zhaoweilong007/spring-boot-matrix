package com.zwl;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 库存服务
 *
 * @author ZhaoWeiLong
 * @since 2021/11/12
 */
@SpringBootApplication
@EnableAutoDataSourceProxy
@EnableDubbo
@EnableTransactionManagement
public class StorageApp {
  public static void main(String[] args) {
    SpringApplication.run(StorageApp.class, args);
  }
}
