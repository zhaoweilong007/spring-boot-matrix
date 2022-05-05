package com.zwl.pubsub;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zwl.model.AbstractMessage;

/**
 * 描述：pub/sub 消息实体
 *
 * @author zwl
 * @since 2022/3/10 9:20
 */
public abstract class AbstractChannelMessage extends AbstractMessage {

  /**
   * 获取channel通道
   *
   * @return channel
   */
  @JsonIgnore
  public abstract String getChannel();
}
