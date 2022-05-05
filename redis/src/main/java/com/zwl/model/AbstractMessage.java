package com.zwl.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：订阅消息实体
 *
 * @author zwl
 * @since 2022/3/10 9:12
 */
public abstract class AbstractMessage {

  @Setter @Getter private Map<String, String> headers = new HashMap<>();

  public String header(String key) {
    return headers.get(key);
  }

  public <T extends AbstractMessage> T addHeader(String key, String val) {
    headers.put(key, val);
    return (T) this;
  }
}
