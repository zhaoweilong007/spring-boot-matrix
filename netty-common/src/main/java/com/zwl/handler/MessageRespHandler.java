package com.zwl.handler;

import com.zwl.model.MessageResponsePacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息响应处理器
 *
 * @author ZhaoWeiLong
 * @since 2021/8/17
 */
@Slf4j
@Sharable
public class MessageRespHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

  public static final MessageRespHandler INSTANCE = new MessageRespHandler();

  @Override
  protected void channelRead0(
      ChannelHandlerContext channelHandlerContext, MessageResponsePacket msg) {
    log.info(
        "[收到消息] 【{}】:【{}】 -> {}", msg.getFromUserName(), msg.getFromUserId(), msg.getMessage());
  }
}
