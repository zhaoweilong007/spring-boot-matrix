package com.zwl.handler;

import com.zwl.model.HeartBeatRespPacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/20
 **/
@Slf4j
@Sharable
public class HeartBeatRespHandler extends SimpleChannelInboundHandler<HeartBeatRespPacket> {

  public static final HeartBeatRespHandler INSTANCE = new HeartBeatRespHandler();

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext,
      HeartBeatRespPacket respPacket) throws Exception {
    log.debug("============收到心跳包响应===============");
  }
}
