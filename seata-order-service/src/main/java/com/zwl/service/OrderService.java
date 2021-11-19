package com.zwl.service;

import com.zwl.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface OrderService extends IService<Order> {

  Integer createOrder(Integer userId, Integer storageId, Integer num);
}
