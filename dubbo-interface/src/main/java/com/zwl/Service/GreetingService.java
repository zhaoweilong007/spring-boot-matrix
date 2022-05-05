package com.zwl.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/22
 */
public interface GreetingService {

  String greeting(String name);

  default String replyGreeting(String name) {
    return "Fine," + name;
  }

  default CompletableFuture<String> greeting(String name, byte[] sign) {
    return CompletableFuture.completedFuture(greeting(name));
  }
}
