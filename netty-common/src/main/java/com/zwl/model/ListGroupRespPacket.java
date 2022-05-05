package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 列出群聊响应
 *
 * @author ZhaoWeiLong
 * @since 2021/8/19
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ListGroupRespPacket extends Packet {

  @Override
  public Byte getCommand() {
    return Command.LIST_GROUP_RESPONSE.getCode().byteValue();
  }
}
