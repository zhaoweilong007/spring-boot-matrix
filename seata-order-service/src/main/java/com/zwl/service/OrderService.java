package com.zwl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zwl.domain.Order;

/** */
public interface OrderService extends IService<Order> {

  Integer createOrder(Integer userId, Integer storageId, Integer num);
}
