package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 退出群聊请求
 * @author ZhaoWeiLong
 * @since 2021/8/19
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class QuitGroupReqPacket extends Packet {

  private String groupId;

  @Override
  public Byte getCommand() {
    return Command.QUIT_GROUP_REQUEST.getCode().byteValue();
  }
}
