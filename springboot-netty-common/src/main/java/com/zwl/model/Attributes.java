package com.zwl.model;

import io.netty.util.AttributeKey;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/16
 */
public interface Attributes {

  /** 登录标识 */
  AttributeKey<Boolean> LOGIN = AttributeKey.valueOf("login");
  /** 会话标识 */
  AttributeKey<Session> SESSION = AttributeKey.valueOf("session");
}
