package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ZhaoWeiLong
 * @since 2021/7/30
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class LoginRespPacket extends Packet {

  private String msg;
  private Boolean success;
  private String userId;

  @Override
  public Byte getCommand() {
    return Command.LOGIN_RESPONSE.getCode().byteValue();
  }
}
