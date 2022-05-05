package com.zwl.grpc.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;

/**
 * @author ZhaoWeiLong
 * @since 2021/9/6
 */
@GrpcGlobalServerInterceptor
@Slf4j
public class LogInterceptor implements ServerInterceptor {

  @Override
  public <ReqT, RespT> Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
    log.info("serviceName:{}", call.getMethodDescriptor().getServiceName());
    log.info("fullMethodName:{}", call.getMethodDescriptor().getFullMethodName());
    log.info("header:{}", headers);
    return next.startCall(call, headers);
  }
}
