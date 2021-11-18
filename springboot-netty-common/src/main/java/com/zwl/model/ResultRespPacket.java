package com.zwl.model;

import lombok.*;

/**
 * 统一响应
 *
 * @author ZhaoWeiLong
 * @since 2021/8/20
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultRespPacket<T> extends Packet {

  private Boolean success;

  private String msg;

  private T data;

  @Override
  public Byte getCommand() {
    return Command.RESULT_RESPONSE.getCode().byteValue();
  }
}
