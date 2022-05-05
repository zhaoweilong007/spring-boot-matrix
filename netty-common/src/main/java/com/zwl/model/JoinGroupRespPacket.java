package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 加入群聊响应
 *
 * @author ZhaoWeiLong
 * @since 2021/8/19
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class JoinGroupRespPacket extends Packet {

  private Boolean success;

  @Override
  public Byte getCommand() {
    return Command.JOIN_GROUP_RESPONSE.getCode().byteValue();
  }
}
