package com.zwl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

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

  private RestTemplate restTemplate = new RestTemplate();

  public static void main(String[] args) {
    SpringApplication.run(ElkApp.class, args);
  }

  @Scheduled(fixedRate = 1000*5)
  public void test() {
    log.info("当前时间:{},线程名称:{}", DateUtil.now(), Thread.currentThread().getName());
    ResponseEntity<String> forEntity =
        restTemplate.getForEntity(
            "http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_key=关键字&bk_length=600\n",
            String.class);
    log.info("请求响应:{}", forEntity);
  }
}
