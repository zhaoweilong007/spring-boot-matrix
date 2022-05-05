package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 创建群聊响应
 *
 * @author ZhaoWeiLong
 * @since 2021/8/19
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class CreateGroupRespPacket extends Packet {

  private String groupId;

  private List<String> userNames;

  private Boolean success;

  @Override
  public Byte getCommand() {
    return Command.CREATE_GROUP_RESPONSE.getCode().byteValue();
  }
}
