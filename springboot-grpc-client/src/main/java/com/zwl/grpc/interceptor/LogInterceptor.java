package com.zwl.grpc.interceptor;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.MethodDescriptor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;

/**
 * @author ZhaoWeiLong
 * @since 2021/9/6
 */
@GrpcGlobalClientInterceptor
@Slf4j
public class LogInterceptor implements ClientInterceptor {

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
    log.info("fullMethodName:{}", method.getFullMethodName());
    return next.newCall(method, callOptions);
  }
}
