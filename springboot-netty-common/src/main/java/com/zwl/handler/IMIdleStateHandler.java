package com.zwl.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务器空闲检测
 *
 * @author ZhaoWeiLong
 * @since 2021/8/20
 **/
@Slf4j
public class IMIdleStateHandler extends IdleStateHandler {

  public IMIdleStateHandler() {
    //读空闲时间，写空闲时间，读写空闲时间，只要在一定时间内没有产生数据读写，就认为连接假死
    super(30, 0, 0, TimeUnit.SECONDS);
  }

  /**
   * 连接假死处理
   *
   * @param ctx
   * @param evt
   * @throws Exception
   */
  @Override
  protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
    log.info("30秒内未读到数据，关闭连接");
    ctx.channel().close();
  }
}
