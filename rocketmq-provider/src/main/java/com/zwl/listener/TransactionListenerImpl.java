package com.zwl.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/30
 **/
@RocketMQTransactionListener
@Slf4j
@Component
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

  /**
   * 执行本地事务
   *
   * @param msg
   * @param arg
   * @return
   */
  @Override
  public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
    log.info("invoke executeLocalTransaction msg:{}", msg);
    return RocketMQLocalTransactionState.UNKNOWN;
  }

  /**
   * 检车事务状态
   *
   * @param msg
   * @return
   */
  @Override
  public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
    log.info("invoke checkLocalTransaction msg:{}", msg);
    return RocketMQLocalTransactionState.COMMIT;
  }
}
