package com.zwl.handler;

import com.zwl.model.GroupMessageReqPacket;
import com.zwl.model.GroupMessageRespPacket;
import com.zwl.model.ResultRespPacket;
import com.zwl.utils.LogUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/20
 **/
@Slf4j
@Sharable
public class GroupMessageReqHandler extends SimpleChannelInboundHandler<GroupMessageReqPacket> {

  public static final GroupMessageReqHandler INSTANCE = new GroupMessageReqHandler();


  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
      GroupMessageReqPacket reqPacket) throws Exception {
    log.info("【群聊消息】,data:{}", reqPacket);
    ChannelGroup groupMap = LogUtils.getGroupMap(reqPacket.getGroupId());
    if (groupMap == null) {
      ResultRespPacket<String> respPacket = new ResultRespPacket<>();
      respPacket.setSuccess(false);
      respPacket.setMsg("群聊id不存在，请检查重试");
      ctx.channel().writeAndFlush(respPacket);
    } else {
      GroupMessageRespPacket respPacket = new GroupMessageRespPacket();
      respPacket.setGroupId(reqPacket.getGroupId());
      respPacket.setMessage(reqPacket.getMessage());
      respPacket.setFromUser(LogUtils.getSession(ctx.channel()));
      groupMap.writeAndFlush(respPacket);
    }

  }
}
