package com.zwl.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试ChannelInboundHandlerAdapter生命周期
 *
 * @author ZhaoWeiLong
 * @since 2021/8/18
 */
@Slf4j
public class LeftCycleTestHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    log.info("handlerAdded 调用。。。");
    super.handlerAdded(ctx);
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    log.info("handlerRemoved 调用。。。");
    super.handlerRemoved(ctx);
  }

  @Override
  public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    log.info("channelRegistered 调用。。。");
    super.channelRegistered(ctx);
  }

  @Override
  public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    log.info("channelUnregistered 调用。。。");
    super.channelUnregistered(ctx);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    log.info("channelActive 调用。。。");
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    log.info("channelInactive 调用。。。");
    super.channelInactive(ctx);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    log.info("channelRead 调用。。。");

    super.channelRead(ctx, msg);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    log.info("channelReadComplete 调用。。。");

    super.channelReadComplete(ctx);
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    log.info("userEventTriggered 调用。。。");

    super.userEventTriggered(ctx, evt);
  }

  @Override
  public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
    log.info("channelWritabilityChanged 调用。。。");

    super.channelWritabilityChanged(ctx);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    log.info("exceptionCaught 调用。。。");

    super.exceptionCaught(ctx, cause);
  }
}
