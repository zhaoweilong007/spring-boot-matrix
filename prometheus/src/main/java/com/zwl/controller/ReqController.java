package com.zwl.controller;

import com.zwl.monitor.PrometheusMonitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * 描述：使用MeterRegistry埋点监控
 *
 * @author zwl
 * @since 2022/5/7 10:09
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class ReqController {

  private final PrometheusMonitor monitor;

  /** 模拟下单请求 */
  @GetMapping("/order")
  public String order(@RequestParam(required = false, defaultValue = "0") Long orderId) {
    log.info("orderId: {}", orderId);

    if (orderId <= 0) {
      throw new RuntimeException("orderId必须大于0");
    }

    // 记录下单次数
    monitor.getOrderCount().increment();

    Random random = new Random();
    int amount = random.nextInt(100);
    // 记录下单总金额
    monitor.getOrderAmount().record(amount);
    return "success";
  }
}
