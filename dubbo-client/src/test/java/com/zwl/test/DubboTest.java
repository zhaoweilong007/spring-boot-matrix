package com.zwl.test;

import com.zwl.Service.GreetingService;
import com.zwl.constant.DubboVersion;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/22
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class DubboTest {

  @DubboReference(version = DubboVersion.VERSION)
  GreetingService greetingService;

  @Test
  public void test() throws ExecutionException, InterruptedException {
    greetingService.greeting("dubbo");
    CompletableFuture<String> greetFuture = RpcContext.getContext().getCompletableFuture();
    String msg = greetFuture.get();
    System.out.println(msg);
  }
}
