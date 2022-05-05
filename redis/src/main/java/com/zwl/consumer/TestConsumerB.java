package com.zwl.consumer;

import com.alibaba.fastjson.JSON;
import com.zwl.model.SMessage;
import com.zwl.stream.AbstractStreamListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/7 10:54
 */
@Component
@Slf4j
public class TestConsumerB extends AbstractStreamListener<SMessage> {

  @Override
  public void onMessage(SMessage message) {
    log.info(
        "{} TestConsumerB receiver message:{}",
        Thread.currentThread().getName(),
        JSON.toJSONString(message));
  }
}
