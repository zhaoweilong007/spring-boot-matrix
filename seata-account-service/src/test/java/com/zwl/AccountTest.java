package com.zwl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zwl.domain.User;
import com.zwl.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class AccountTest {

  @Autowired UserMapper userMapper;

  @Test
  public void test() {
    User user = new User();
    user.setUserName("张三");
    user.setAmount(100.0D);
    user.setCreateTime(new Date());
    user.setUpdateTime(new Date());
    userMapper.insert(user);

    List<User> users = userMapper.selectList(new QueryWrapper<>());
    log.info("users:{}", users);

    userMapper.deductBalance(user.getId(), 10.00);
  }
}
