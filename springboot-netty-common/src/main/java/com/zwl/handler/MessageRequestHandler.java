package com.zwl.handler;


import com.zwl.model.MessageRequestPacket;
import com.zwl.model.MessageResponsePacket;
import com.zwl.model.Session;
import com.zwl.utils.LogUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息请求处理器
 *
 * @author ZhaoWeiLong
 * @since 2021/8/17
 **/
@Slf4j
@Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

  public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();


  @Override
  protected void channelRead0(ChannelHandlerContext ctx,
      MessageRequestPacket messageRequestPacket) throws Exception {
    String message = messageRequestPacket.getMessage();
    String toUserId = messageRequestPacket.getToUserId();
    Session session = LogUtils.getSession(ctx.channel());

    log.info("收到用户:{}发送给用户：{}的消息：{}", session.getUserId(), toUserId, message);

    MessageResponsePacket responsePacket = new MessageResponsePacket(message, session.getUserId(),
        session.getUserName());

    Channel channel = LogUtils.getChannel(toUserId);
    if (channel != null && LogUtils.hasSession(channel)) {
      channel.writeAndFlush(responsePacket);
    } else {
      responsePacket.setFromUserName("服务器");
      responsePacket.setFromUserId("");
      responsePacket.setMessage(String.format("用户%s不在线，发送失败", toUserId));
      ctx.writeAndFlush(responsePacket);
    }

  }
}
