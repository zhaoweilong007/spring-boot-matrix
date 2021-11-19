package com.zwl.controller;

import com.zwl.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("createOrder")
  public Integer createOrder(
      @RequestParam("userId") Integer userId,
      @RequestParam("storageId") Integer storageId,
      @RequestParam("num") Integer num) {
    log.info("创建订单：userId:{},storageId:{},num:{}", userId, storageId, num);
    return orderService.createOrder(userId, storageId, num);
  }
}
