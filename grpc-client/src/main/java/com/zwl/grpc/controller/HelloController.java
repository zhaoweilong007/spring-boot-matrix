package com.zwl.grpc.controller;

import com.zwl.grpc.HelloReq;
import com.zwl.grpc.HelloResp;
import com.zwl.grpc.HelloServiceGrpc.HelloServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhaoWeiLong
 * @since 2021/9/6
 */
@RestController
public class HelloController {

  @GrpcClient("local-grpc-server")
  private HelloServiceBlockingStub helloServiceBlockingStub;

  @RequestMapping("/hello")
  public String each(@RequestParam("name") String name) {
    HelloResp helloResp =
        helloServiceBlockingStub.sayHello(HelloReq.newBuilder().setName(name).build());
    return helloResp.getMsg();
  }
}
