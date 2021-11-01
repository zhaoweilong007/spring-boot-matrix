package com.zwl.handler;

import cn.hutool.core.util.StrUtil;
import com.zwl.model.JoinGroupReqPacket;
import com.zwl.model.ResultRespPacket;
import com.zwl.model.Session;
import com.zwl.utils.LogUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/19
 **/
@Slf4j
@Sharable
public class JoinGroupReqHandler extends SimpleChannelInboundHandler<JoinGroupReqPacket> {

  public static final JoinGroupReqHandler INSTANCE = new JoinGroupReqHandler();


  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext,
      JoinGroupReqPacket joinGroupReqPacket) throws Exception {
    log.info("【拉人群聊】，data:{}",joinGroupReqPacket);
    String groupId = joinGroupReqPacket.getGroupId();
    List<String> userIds = joinGroupReqPacket.getUserIds();
    ChannelGroup groupMap = LogUtils.getGroupMap(groupId);
    ResultRespPacket<String> respPacket = ResultRespPacket.<String>builder().build();
    Session session = LogUtils.getSession(channelHandlerContext.channel());
    if (groupMap != null) {
      StringBuilder uids = new StringBuilder();
      for (String userId : userIds) {
        Channel channel = LogUtils.getChannel(userId);
        if (channel != null) {
          sendMsg(channel, session.getUserName(), groupId);
          groupMap.add(channelHandlerContext.channel());
        } else {
          uids.append(userId);
        }
      }
      respPacket.setSuccess(true);
      respPacket.setMsg("加入群聊成功，部分用户加入失败：" + uids);
    } else {
      respPacket.setSuccess(false);
      respPacket.setMsg("加入群聊失败，群聊不存在");
    }
    channelHandlerContext.writeAndFlush(respPacket);
  }

  private void sendMsg(Channel channel, String userName, String groupId) {
    ResultRespPacket<Object> respPacket = ResultRespPacket.builder().success(true)
        .msg(StrUtil.format("你被{}邀请加入群聊，群聊id:{}", userName, groupId)).build();
    channel.writeAndFlush(respPacket);
  }
}
