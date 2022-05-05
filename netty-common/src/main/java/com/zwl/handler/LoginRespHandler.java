package com.zwl.handler;

import com.zwl.model.LoginRespPacket;
import com.zwl.utils.LogUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 、 登录响应处理器
 *
 * @author ZhaoWeiLong
 * @since 2021/8/17
 */
@Slf4j
@Sharable
public class LoginRespHandler extends SimpleChannelInboundHandler<LoginRespPacket> {

  public static final LoginRespHandler INSTANCE = new LoginRespHandler();

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    log.info("客户端连接被关闭");
    super.channelInactive(ctx);
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, LoginRespPacket loginRespPacket) {
    if (loginRespPacket.getSuccess()) {
      LogUtils.markAsLogin(ctx.channel());
    }
    log.info(loginRespPacket.getMsg());
  }
}
