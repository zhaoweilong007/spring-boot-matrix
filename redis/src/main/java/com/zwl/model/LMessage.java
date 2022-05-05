package com.zwl.model;

import com.zwl.pubsub.AbstractChannelMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/10 15:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class LMessage extends AbstractChannelMessage {

  String name;

  ArrayList<String> lists;

  @Override
  public String getChannel() {
    return "queueA";
  }
}
