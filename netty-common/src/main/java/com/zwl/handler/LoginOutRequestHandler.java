package com.zwl.handler;

import com.zwl.model.Attributes;
import com.zwl.model.LoginOutRequestPacket;
import com.zwl.utils.LogUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 登出处理器
 *
 * @author ZhaoWeiLong
 * @since 2021/8/19
 */
@Slf4j
@Sharable
public class LoginOutRequestHandler extends SimpleChannelInboundHandler<LoginOutRequestPacket> {

  public static final LoginOutRequestHandler INSTANCE = new LoginOutRequestHandler();

  @Override
  protected void channelRead0(
      ChannelHandlerContext channelHandlerContext, LoginOutRequestPacket loginOutRequestPacket)
      throws Exception {
    LogUtils.unBindSession(channelHandlerContext.channel());
    channelHandlerContext.channel().attr(Attributes.LOGIN).set(null);
    channelHandlerContext.channel().close();
  }
}
