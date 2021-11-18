package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录指令
 *
 * @author ZhaoWeiLong
 * @since 2021/7/27
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class LoginRequestPacket extends Packet {

  private String userId;

  private String userName;

  private String password;

  @Override
  public Byte getCommand() {
    return Command.LOGIN_REQUEST.getCode().byteValue();
  }
}
