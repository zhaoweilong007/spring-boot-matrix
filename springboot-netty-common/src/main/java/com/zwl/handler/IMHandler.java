package com.zwl.handler;

import com.zwl.model.Command;
import com.zwl.model.Packet;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一处理器，缩短handler的处理路径
 *
 * @author ZhaoWeiLong
 * @since 2021/8/20
 **/
@Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

  public static final IMHandler INSTANCE = new IMHandler();

  private final Map<Integer, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

  private IMHandler() {
    handlerMap = new HashMap<>();
    handlerMap.put(Command.LOGIN_OUT_REQUEST.getCode(), LoginOutRequestHandler.INSTANCE);
    handlerMap.put(Command.MESSAGE_REQUEST.getCode(), MessageRequestHandler.INSTANCE);
    handlerMap.put(Command.MESSAGE_RESPONSE.getCode(), MessageRespHandler.INSTANCE);
    handlerMap.put(Command.CREATE_GROUP_REQUEST.getCode(), CreateGroupRequestHandler.INSTANCE);
    handlerMap.put(Command.CREATE_GROUP_RESPONSE.getCode(), CreateGroupRespHandler.INSTANCE);
    handlerMap.put(Command.JOIN_GROUP_REQUEST.getCode(), JoinGroupReqHandler.INSTANCE);
    handlerMap.put(Command.QUIT_GROUP_REQUEST.getCode(), QuitGroupReqHandler.INSTANCE);
    handlerMap.put(Command.LIST_GROUP_REQUEST.getCode(), ListGroupReqHandler.INSTANCE);
    handlerMap.put(Command.RESULT_RESPONSE.getCode(), ResultRespHandler.INSTANCE);
    handlerMap.put(Command.GROUP_MESSAGE_REQUEST.getCode(), GroupMessageReqHandler.INSTANCE);
    handlerMap.put(Command.GROUP_MESSAGE_RESPONSE.getCode(), GroupMessageRespHandler.INSTANCE);
  }


  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet)
      throws Exception {
    SimpleChannelInboundHandler<? extends Packet> handler = handlerMap.get(
        packet.getCommand().intValue());
    handler.channelRead(channelHandlerContext, packet);
  }
}
