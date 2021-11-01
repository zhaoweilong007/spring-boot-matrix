package com.zwl.handler;


import com.zwl.model.ListGroupReqPacket;
import com.zwl.model.ResultRespPacket;
import com.zwl.model.ResultRespPacket.ResultRespPacketBuilder;
import com.zwl.model.Session;
import com.zwl.utils.LogUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * 列出群聊请求处理器
 *
 * @author ZhaoWeiLong
 * @since 2021/8/20
 **/
@Slf4j
@Sharable
public class ListGroupReqHandler extends SimpleChannelInboundHandler<ListGroupReqPacket> {

  public static final ListGroupReqHandler INSTANCE = new ListGroupReqHandler();

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext,
      ListGroupReqPacket listGroupReqPacket) throws Exception {
    String groupId = listGroupReqPacket.getGroupId();
    ChannelGroup groupMap = LogUtils.getGroupMap(groupId);
    ResultRespPacketBuilder<Object> packetBuilder = ResultRespPacket.builder();
    if (groupMap == null) {
      channelHandlerContext
          .writeAndFlush(packetBuilder.success(false).msg("群聊不存在").build());
    } else {
      List<String> list = groupMap.stream().map(channel -> {
        Session session = LogUtils.getSession(channel);
        return session.getUserId() + ":" + session.getUserName();
      }).collect(Collectors.toList());
      channelHandlerContext.writeAndFlush(
          packetBuilder.success(true).msg("当前群聊成员:").data(list).build());
    }
  }
}
