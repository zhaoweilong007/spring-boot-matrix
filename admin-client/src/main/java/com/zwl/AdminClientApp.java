package com.zwl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author zwl
 * @since ${DATE} ${TIME}
 */
@SpringBootApplication
@RestController
public class AdminClientApp {
  public static void main(String[] args) {
    SpringApplication.run(AdminClientApp.class, args);
  }

  @GetMapping
  public String hello() {
    return "Hello World";
  }
}
