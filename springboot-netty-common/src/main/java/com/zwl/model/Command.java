package com.zwl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 指令
 *
 * @author ZhaoWeiLong
 * @since 2021/7/27
 **/
@Getter
@AllArgsConstructor
public enum Command {

  /**
   * 登录请求
   */
  LOGIN_REQUEST(1, LoginRequestPacket.class),

  /**
   * 登录响应
   */
  LOGIN_RESPONSE(2, LoginRespPacket.class),

  LOGIN_OUT_REQUEST(3, LoginOutRequestPacket.class),

  LOGIN_OUT_RESPONSE(4, LoginOutRequestPacket.class),

  /**
   * 消息请求
   */
  MESSAGE_REQUEST(5, MessageRequestPacket.class),

  /**
   * 消息响应
   */
  MESSAGE_RESPONSE(6, MessageResponsePacket.class),

  /**
   * 创建群聊请求
   */
  CREATE_GROUP_REQUEST(7, CreateGroupRequestPacket.class),

  /**
   * 创建群聊响应
   */
  CREATE_GROUP_RESPONSE(8, CreateGroupRespPacket.class),

  JOIN_GROUP_REQUEST(9, JoinGroupReqPacket.class),

  JOIN_GROUP_RESPONSE(10, JoinGroupRespPacket.class),

  QUIT_GROUP_REQUEST(10, QuitGroupReqPacket.class),

  QUIT_GROUP_RESPONSE(11, QuitGroupRespPacket.class),

  LIST_GROUP_REQUEST(12, ListGroupReqPacket.class),

  LIST_GROUP_RESPONSE(13, ListGroupRespPacket.class),

  RESULT_RESPONSE(14, ResultRespPacket.class),

  GROUP_MESSAGE_REQUEST(15, GroupMessageReqPacket.class),

  GROUP_MESSAGE_RESPONSE(16, GroupMessageRespPacket.class),

  HEART_BEAT_REQUEST(17, HeartBeatRequestPacket.class),

  HEART_BEAT_RESPONSE(18, HeartBeatRespPacket.class);


  private final Integer code;
  private final Class<? extends Packet> clazz;
}
