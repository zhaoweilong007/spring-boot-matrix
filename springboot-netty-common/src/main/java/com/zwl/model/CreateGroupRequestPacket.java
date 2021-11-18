package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 创建群聊请求
 *
 * @author ZhaoWeiLong
 * @since 2021/8/19
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class CreateGroupRequestPacket extends Packet {

  private List<String> userIds;

  @Override
  public Byte getCommand() {
    return Command.CREATE_GROUP_REQUEST.getCode().byteValue();
  }
}
