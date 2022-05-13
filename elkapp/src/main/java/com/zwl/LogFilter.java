package com.zwl;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述：日志请求id
 *
 * @author zwl
 * @since 2022/5/12 17:29
 */
@Component
@Slf4j
public class LogFilter extends AbstractRequestLoggingFilter {
  public static final String TRACE_KEY = "traceId";

  @Override
  protected void beforeRequest(HttpServletRequest request, String message) {
    MDC.put(TRACE_KEY, UUID.fastUUID().toString());
  }

  @Override
  protected void afterRequest(HttpServletRequest request, String message) {
    MDC.clear();
  }
}
