package com.zwl.springbootdocker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/5/11 12:08
 */
@RestController
@Slf4j
public class TestController {

  @Value("${var.msg}")
  private String msg;

  @GetMapping("/msg")
  public String msg() {
    return msg;
  }
}
