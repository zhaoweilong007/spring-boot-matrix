package com.zwl.handler;

import com.alibaba.fastjson.JSON;
import com.zwl.model.ResultRespPacket;
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
public class ResultRespHandler extends SimpleChannelInboundHandler<ResultRespPacket> {

  public static final ResultRespHandler INSTANCE = new ResultRespHandler();

  @Override
  protected void channelRead0(
      ChannelHandlerContext channelHandlerContext, ResultRespPacket resultRespPacket)
      throws Exception {
    log.info(
        "执行指令{}，msg:{},data:{}",
        resultRespPacket.getSuccess() ? "成功" : "失败",
        resultRespPacket.getMsg(),
        JSON.toJSONString(resultRespPacket.getData()));
  }
}
