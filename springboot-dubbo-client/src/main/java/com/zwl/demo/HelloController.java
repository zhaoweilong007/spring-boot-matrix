package com.zwl.demo;

import com.zwl.Service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/21
 **/
@RestController
@Slf4j
public class HelloController {

  @DubboReference HelloService helloService;

  @GetMapping("/hello/{name}")
  public String sayHello(@PathVariable("name")String name) {
    return helloService.sayHello(name);
  }
}
