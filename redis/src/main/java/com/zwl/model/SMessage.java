package com.zwl.model;

import com.zwl.stream.AbstractStreamMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/4 18:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SMessage extends AbstractStreamMessage {

  Long senderId;
  Long receiverId;
  String content;
  BigDecimal count;
  List<String> lists;

  @Override
  public String getStreamKey() {
    return "stream.queue.a";
  }
}
