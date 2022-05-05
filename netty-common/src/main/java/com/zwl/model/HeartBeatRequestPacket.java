package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/20
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class HeartBeatRequestPacket extends Packet {

  private Date date;

  @Override
  public Byte getCommand() {
    return Command.HEART_BEAT_REQUEST.getCode().byteValue();
  }
}
