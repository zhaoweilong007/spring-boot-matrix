package com.zwl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/20
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class HeartBeatRespPacket extends Packet {

  @Override
  public Byte getCommand() {
    return Command.HEART_BEAT_RESPONSE.getCode().byteValue();
  }
}
