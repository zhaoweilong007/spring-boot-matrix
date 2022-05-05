package com.zwl.handler;

import com.zwl.model.HeartBeatRequestPacket;
import com.zwl.model.HeartBeatRespPacket;
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
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

  public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

  @Override
  protected void channelRead0(
      ChannelHandlerContext channelHandlerContext, HeartBeatRequestPacket heartBeatRequestPacket)
      throws Exception {
    log.debug("=======收到心跳包请求========");
    channelHandlerContext.channel().writeAndFlush(new HeartBeatRespPacket());
  }
}
