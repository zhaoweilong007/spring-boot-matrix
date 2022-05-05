package com.zwl.grpc.config;

import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import net.devh.boot.grpc.server.scope.GrpcRequestScope;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhaoWeiLong
 * @since 2021/9/6
 */
@Configuration(proxyBeanMethods = false)
public class GrpcServerConfig {

  @Bean
  public GrpcServerConfigurer keepAliveServerConfiguration() {
    return serverBuilder -> {
      ((NettyServerBuilder) serverBuilder)
          .keepAliveTime(30, TimeUnit.SECONDS)
          .keepAliveTimeout(5, TimeUnit.SECONDS)
          .permitKeepAliveWithoutCalls(true);
    };
  }

  @Bean
  @Scope(
      scopeName = GrpcRequestScope.GRPC_REQUEST_SCOPE_NAME,
      proxyMode = ScopedProxyMode.TARGET_CLASS)
  public ScopedBean myScopedBean() {
    return new ScopedBean();
  }

  public static class ScopedBean {}
}
