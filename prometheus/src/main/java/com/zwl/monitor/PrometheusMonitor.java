package com.zwl.monitor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：自定义监控
 *
 * @author zwl
 * @since 2022/5/7 10:11
 */
@Component
public class PrometheusMonitor implements InitializingBean {

  /** 描述：请求错误次数 */
  @Getter @Setter private Counter requestErrorCount;

  /** 描述：下单次数 */
  @Getter @Setter private Counter orderCount;

  /** 下单总金额 */
  @Getter @Setter private DistributionSummary orderAmount;

  private final MeterRegistry registry;

  @Autowired
  public PrometheusMonitor(MeterRegistry registry) {
    this.registry = registry;
  }

  @Override
  public void afterPropertiesSet() {
    requestErrorCount = registry.counter("request_error_count", "reqErrorTotal", "error");
    orderCount = registry.counter("order_count", "orderTotal", "order");
    orderAmount = registry.summary("order_amount", "orderAmount", "amount");
  }
}
