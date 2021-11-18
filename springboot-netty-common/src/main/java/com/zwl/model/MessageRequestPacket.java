package com.zwl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/16
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class MessageRequestPacket extends Packet {

  private String toUserId;
  private String message;

  @Override
  public Byte getCommand() {
    return Command.MESSAGE_REQUEST.getCode().byteValue();
  }
}
