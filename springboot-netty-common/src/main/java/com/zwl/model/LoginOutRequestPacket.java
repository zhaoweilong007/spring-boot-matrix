package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * 登出
 *
 * @author ZhaoWeiLong
 * @since 2021/8/19
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class LoginOutRequestPacket extends Packet {

  private String userId;

  @Override
  public Byte getCommand() {
    return Command.LOGIN_OUT_REQUEST.getCode().byteValue();
  }

}
