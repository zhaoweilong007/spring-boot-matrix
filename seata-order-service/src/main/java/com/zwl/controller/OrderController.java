package com.zwl.controller;

import com.zwl.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/18
 */
@RestController
@Slf4j
public class OrderController {

  @Autowired private OrderService orderService;

  @PostMapping("createOrder")
  public Integer createOrder(
      @RequestParam Integer userId, @RequestParam Integer storageId, @RequestParam Integer num) {
    log.info("创建订单：{}", userId, storageId, num);
    return orderService.createOrder(userId, storageId, num);
  }
}
