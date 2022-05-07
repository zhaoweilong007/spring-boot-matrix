package com.zwl.exception;

import com.zwl.monitor.PrometheusMonitor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 描述：全局异常捕获
 *
 * @author zwl
 * @since 2022/5/7 10:27
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final PrometheusMonitor monitor;

  @ExceptionHandler(value = Exception.class)
  public String exceptionHandler(Exception e) {
    // 记录请求失败次数
    monitor.getRequestErrorCount().increment();
    return e.getMessage();
  }
}
