package com.zwl.service;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/12
 */
public interface AccountRpcService {

  Boolean checkUser(Integer userId);

  Boolean deductBalance(Integer userId, Double amount);
}
