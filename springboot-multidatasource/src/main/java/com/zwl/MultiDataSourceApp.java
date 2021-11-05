package com.zwl;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/4
 **/
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class MultiDataSourceApp {

  public static void main(String[] args) {
    SpringApplication.run(MultiDataSourceApp.class, args);
  }

}
