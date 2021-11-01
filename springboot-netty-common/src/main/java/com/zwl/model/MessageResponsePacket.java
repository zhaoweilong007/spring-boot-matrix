package com.zwl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/16
 **/
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class MessageResponsePacket extends Packet {

  private String message;
  private String fromUserId;
  private String fromUserName;

  @Override
  public Byte getCommand() {
    return Command.MESSAGE_RESPONSE.getCode().byteValue();
  }
}
