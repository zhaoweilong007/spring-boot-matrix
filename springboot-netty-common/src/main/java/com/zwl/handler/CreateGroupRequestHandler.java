package com.zwl.handler;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.zwl.model.CreateGroupRequestPacket;
import com.zwl.model.CreateGroupRespPacket;
import com.zwl.model.ResultRespPacket;
import com.zwl.utils.LogUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 创建群聊处理器
 *
 * @author ZhaoWeiLong
 * @since 2021/8/19
 */
@Slf4j
@Sharable
public class CreateGroupRequestHandler
    extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

  public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

  @Override
  protected void channelRead0(
      ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket)
      throws Exception {
    log.info("【创建群聊】，data:{}", createGroupRequestPacket);
    List<String> userIds = createGroupRequestPacket.getUserIds();
    DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
    List<String> userNames =
        userIds.stream()
            .map(
                id -> {
                  Channel channel = LogUtils.getChannel(id);
                  if (channel != null) {
                    channelGroup.add(channel);
                    return LogUtils.getSession(channel).getUserName();
                  }
                  return null;
                })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    String userName = LogUtils.getSession(ctx.channel()).getUserName();

    String uuid = IdUtil.randomUUID();
    String groupId = uuid.substring(0, uuid.indexOf("-"));
    LogUtils.putGroupMap(groupId, channelGroup);

    ResultRespPacket<Object> resultRespPacket =
        ResultRespPacket.builder()
            .success(true)
            .msg(StrUtil.format("你被{}邀请加入群聊，群聊id:{}", userName, groupId))
            .build();
    channelGroup.writeAndFlush(resultRespPacket);

    channelGroup.add(ctx.channel());
    userNames.add(userName);

    CreateGroupRespPacket respPacket = new CreateGroupRespPacket();
    respPacket.setGroupId(groupId);
    respPacket.setSuccess(true);
    respPacket.setUserNames(userNames);
    ctx.writeAndFlush(respPacket);
    log.info("创建群聊成功,groupId:{},群成员：{}", groupId, userNames);
  }
}
