package com.zwl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwl.domain.User;
import com.zwl.mapper.UserMapper;
import com.zwl.service.AccountRpcService;
import com.zwl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** */
@Service
@DubboService
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService, AccountRpcService {

  @Override
  public Boolean checkUser(Integer userId) {
    return count(Wrappers.<User>lambdaQuery().eq(User::getId, userId)) == 1;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean deductBalance(Integer userId, Double amount) {
    if (!checkUser(userId)) {
      log.warn("用户不存在：{}", userId);
      throw new RuntimeException("用户不存在");
    }
    return baseMapper.deductBalance(userId, amount);
  }
}
