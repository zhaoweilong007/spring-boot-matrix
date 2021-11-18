package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 列出群聊请求
 *
 * @author ZhaoWeiLong
 * @since 2021/8/19
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ListGroupReqPacket extends Packet {

  private String groupId;

  @Override
  public Byte getCommand() {
    return Command.LIST_GROUP_REQUEST.getCode().byteValue();
  }
}
