package com.zwl.grpc.service;

import com.zwl.grpc.HelloReq;
import com.zwl.grpc.HelloResp;
import com.zwl.grpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author ZhaoWeiLong
 * @since 2021/9/6
 **/
public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {


  @Override
  public void sayHello(HelloReq request, StreamObserver<HelloResp> responseObserver) {
    super.sayHello(request, responseObserver);
  }
}
