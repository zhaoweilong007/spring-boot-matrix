package com.zwl.handler;

import com.zwl.model.QuitGroupReqPacket;
import com.zwl.model.ResultRespPacket;
import com.zwl.utils.LogUtils;
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
public class QuitGroupReqHandler extends SimpleChannelInboundHandler<QuitGroupReqPacket> {

  public static final QuitGroupReqHandler INSTANCE = new QuitGroupReqHandler();

  @Override
  protected void channelRead0(
      ChannelHandlerContext channelHandlerContext, QuitGroupReqPacket quitGroupReqPacket)
      throws Exception {
    ResultRespPacket<String> respPacket = ResultRespPacket.<String>builder().build();
    if (LogUtils.removeGroup(quitGroupReqPacket.getGroupId(), channelHandlerContext.channel())) {
      respPacket.setSuccess(true);
      respPacket.setMsg("退出群聊成功");
    } else {
      respPacket.setSuccess(false);
      respPacket.setMsg("退出群聊失败，群聊不存在");
    }
    channelHandlerContext.writeAndFlush(respPacket);
  }
}
