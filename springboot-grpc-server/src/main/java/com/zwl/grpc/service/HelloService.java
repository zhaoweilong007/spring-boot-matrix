package com.zwl.grpc.service;

import com.zwl.grpc.HelloReq;
import com.zwl.grpc.HelloResp;
import com.zwl.grpc.HelloServiceGrpc;
import com.zwl.grpc.config.GrpcServerConfig.ScopedBean;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ZhaoWeiLong
 * @since 2021/9/6
 */
@GrpcService
@Slf4j
public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {

  // grpc的上下文对象，适用于单个请求
  @Autowired ScopedBean scopedBean;

  @Override
  public void sayHello(HelloReq request, StreamObserver<HelloResp> responseObserver) {
    log.info("收到请求：{}", request);
    HelloResp resp = HelloResp.newBuilder().setMsg("hello " + request.getName()).build();
    responseObserver.onNext(resp);
    responseObserver.onCompleted();
  }
}
