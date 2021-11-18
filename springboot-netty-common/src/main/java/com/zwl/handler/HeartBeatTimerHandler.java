package com.zwl.handler;

import com.zwl.model.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时发送心跳
 *
 * @author ZhaoWeiLong
 * @since 2021/8/20
 */
@Slf4j
@Sharable
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

  public static final HeartBeatTimerHandler INSTANCE = new HeartBeatTimerHandler();

  private static final ScheduledExecutorService scheduledExecutorService =
      Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 5);

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    scheduledExecutorService.scheduleAtFixedRate(
        () -> {
          if (ctx.channel().isActive()) {
            log.debug("============定时发送心跳包==============");
            HeartBeatRequestPacket packet = new HeartBeatRequestPacket();
            packet.setDate(new Date());
            ctx.channel().writeAndFlush(packet);
          } else {
            Thread.currentThread().interrupt();
          }
        },
        0,
        10,
        TimeUnit.SECONDS);
    super.channelActive(ctx);
  }
}
