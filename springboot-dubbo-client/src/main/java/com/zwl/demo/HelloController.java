package com.zwl.demo;

import com.zwl.Service.GreetingService;
import com.zwl.Service.HelloService;
import com.zwl.constant.DubboVersion;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/21
 */
@RestController
@Slf4j
public class HelloController {

  @DubboReference private HelloService helloService;

  @DubboReference(
      interfaceClass = GreetingService.class,
      version = DubboVersion.VERSION,
      timeout = 3000)
  private GreetingService greetingService;

  @GetMapping("/hello/{name}")
  public String sayHello(@PathVariable("name") String name) {
    return helloService.sayHello(name);
  }

  @GetMapping("/greet/{name}")
  public String greeting(@PathVariable("name") String name) {
    return greetingService.greeting(name);
  }

  @GetMapping("/replyGreet/{name}")
  public String replyGreet(@PathVariable("name") String name) {
    return greetingService.replyGreeting(name);
  }

  @GetMapping("/completedFuture/{name}")
  public String completedFuture(@PathVariable("name") String name) throws Exception {
    CompletableFuture<String> future = greetingService.greeting(name, new byte[] {});
    return future.get();
  }
}
