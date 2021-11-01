package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 群聊消息响应
 *
 * @author ZhaoWeiLong
 * @since 2021/8/20
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class GroupMessageRespPacket extends Packet {

  private String message;

  private String groupId;

  private Session fromUser;

  @Override
  public Byte getCommand() {
    return Command.GROUP_MESSAGE_RESPONSE.getCode().byteValue();
  }
}
