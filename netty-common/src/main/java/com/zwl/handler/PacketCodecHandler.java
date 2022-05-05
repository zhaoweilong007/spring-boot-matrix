package com.zwl.handler;

import com.zwl.model.Packet;
import com.zwl.utils.PacketCode;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/20
 */
@Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {

  public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

  @Override
  protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> list)
      throws Exception {
    ByteBuf byteBuf = PacketCode.encode(ctx.alloc().ioBuffer(), packet);
    list.add(byteBuf);
  }

  @Override
  protected void decode(
      ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
      throws Exception {
    list.add(PacketCode.decode(byteBuf));
  }
}
