package com.zwl;

import com.zwl.domain.Order;
import com.zwl.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderTest {

  @Autowired OrderService orderService;

  @Test
  public void test() {

    final List<Order> list =
        orderService.list();
    System.out.println("list="+list);

    Order order = new Order();
    order.setUserId(1);
    order.setStorageId(1);
    order.setPayAmount(12.99);
    order.setUpdateTime(new Date());
    order.setCreateTime(new Date());
    final boolean save = orderService.save(order);
    Assert.assertTrue(save);
  }
}
