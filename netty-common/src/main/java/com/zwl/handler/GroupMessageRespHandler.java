package com.zwl.handler;

import com.zwl.model.GroupMessageRespPacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/20
 */
@Slf4j
@Sharable
public class GroupMessageRespHandler extends SimpleChannelInboundHandler<GroupMessageRespPacket> {

  public static final GroupMessageRespHandler INSTANCE = new GroupMessageRespHandler();

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRespPacket packet)
      throws Exception {
    log.info(
        "[群聊消息]:groupId:【{}】-【{}】:【{}】 -> {}",
        packet.getGroupId(),
        packet.getFromUser().getUserName(),
        packet.getFromUser().getUserId(),
        packet.getMessage());
  }
}
