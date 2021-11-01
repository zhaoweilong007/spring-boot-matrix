package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 退出群聊响应
 * @author ZhaoWeiLong
 * @since 2021/8/19
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class QuitGroupRespPacket extends Packet {

  @Override
  public Byte getCommand() {
    return Command.QUIT_GROUP_RESPONSE.getCode().byteValue();
  }
}
