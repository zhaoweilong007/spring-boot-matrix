package com.zwl.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author ZhaoWeiLong
 * @since 2021/11/22
 */
@Configuration
public class RabbitConfig {

  public static final String QUEUE_NAME = "demo_queue_01";

  public static final String EXCHANGE_NAME = "exchange_demo_01";

  public static final String ROUTING_KEY = "routing_key_01";
}
