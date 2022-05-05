package com.zwl.impl;

import com.zwl.Service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/21
 */
@DubboService
@Slf4j
public class HelloServiceImpl implements HelloService {

  @Override
  public String sayHello(String name) {
    log.info("provider receive invoke of sayHello :{}", name);
    return "hello " + name;
  }
}
