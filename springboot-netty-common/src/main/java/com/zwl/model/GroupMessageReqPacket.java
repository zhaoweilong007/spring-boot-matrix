package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 群聊详细请求
 *
 * @author ZhaoWeiLong
 * @since 2021/8/20
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class GroupMessageReqPacket extends Packet {

  private String message;

  private String groupId;

  @Override
  public Byte getCommand() {
    return Command.GROUP_MESSAGE_REQUEST.getCode().byteValue();
  }
}
