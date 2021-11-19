package com.zwl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwl.domain.Order;
import com.zwl.mapper.OrderMapper;
import com.zwl.service.AccountRpcService;
import com.zwl.service.OrderService;
import com.zwl.service.StorageRpcService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Date;

/** */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

  @DubboReference(
      interfaceClass = AccountRpcService.class,
      version = "1.0.0")
  private AccountRpcService accountRpcService;

  @DubboReference(
      interfaceClass = StorageRpcService.class,
      version = "1.0.0")
  private StorageRpcService storageRpcService;

  /**
   * todo 创建订单分布式事务
   *
   * @param userId 用户id
   * @param storageId 商品id
   * @param num 数量
   * @return 订单号
   */
  @Override
  @GlobalTransactional(rollbackFor = Exception.class)
  public Integer createOrder(Integer userId, Integer storageId, Integer num) {
    Boolean deductStorage = storageRpcService.deductStorage(storageId, num);
    if (!deductStorage) {
      log.error("扣减库存失败");
      return -1;
    }
    var amount = (double) (num * 10);
    Boolean deductBalance = accountRpcService.deductBalance(userId, amount);
    if (!deductBalance) {
      log.warn("扣减余额失败");
      return -1;
    }
    Order order = new Order();
    order.setUserId(userId);
    order.setStorageId(storageId);
    order.setPayAmount(amount);
    order.setUpdateTime(new Date());
    order.setCreateTime(new Date());
    save(order);
    return order.getId();
  }
}
