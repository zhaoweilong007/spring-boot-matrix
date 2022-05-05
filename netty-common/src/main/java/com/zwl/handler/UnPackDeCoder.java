package com.zwl.handler;

import com.zwl.utils.PacketCode;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 拆包器
 *
 * @author ZhaoWeiLong
 * @since 2021/8/18
 */
public class UnPackDeCoder extends LengthFieldBasedFrameDecoder {

  public static final Integer LENGTHENING = 7;
  public static final Integer LENGTH_FIELD_LENGTH = 4;

  public UnPackDeCoder() {
    // 第一个参数为最大容量，第二个参数为长度域偏移量，第三个参数是长度域的长度
    super(Integer.MAX_VALUE, LENGTHENING, LENGTH_FIELD_LENGTH);
  }

  @Override
  protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
    // 判断请求是不是本协议
    if (in.getInt(in.readerIndex()) != PacketCode.MAGIC_NUMS) {
      ctx.channel().close();
      return null;
    }
    return super.decode(ctx, in);
  }
}
