package com.zwl.impl;

import com.zwl.Service.GreetingService;
import com.zwl.constant.DubboVersion;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author ZhaoWeiLong
 * @since 2021/10/22
 */
@DubboService(version = DubboVersion.VERSION)
@Slf4j
public class GreetingServiceImpl implements GreetingService {

  @Override
  public String replyGreeting(String name) {
    log.info("provider receive invoke of replyGreeting :{}", name);

    return "Annotation,Fine " + name;
  }

  @Override
  public String greeting(String name) {
    log.info("provider receive invoke of greeting :{}", name);
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "Annotation,greeting " + name;
  }
}
