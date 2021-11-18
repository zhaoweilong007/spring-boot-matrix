package com.zwl.utils;

import com.zwl.model.Command;
import com.zwl.model.Packet;
import com.zwl.serialize.JsonSerializerImpl;
import com.zwl.serialize.Serializer;
import com.zwl.serialize.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据包编解码
 *
 * @author ZhaoWeiLong
 * @since 2021/7/27
 */
public class PacketCode {

  /** 魔数 */
  public static final int MAGIC_NUMS = 0x12345678;

  public static ConcurrentHashMap<Byte, Serializer> serializerMap = new ConcurrentHashMap<>();

  static {
    serializerMap.put(SerializerAlgorithm.JSON, new JsonSerializerImpl());
  }

  /**
   * 编码
   *
   * @param packet 数据包
   * @return 字节容器
   */
  public static ByteBuf encode(Packet packet) {
    ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
    byte[] bytes = Serializer.DEFAULT.serialize(packet);
    // 数据包由五部分组成 魔数、版本号、序列化算法、指令、数据长度、数据内容
    byteBuf.writeInt(MAGIC_NUMS);
    byteBuf.writeByte(packet.getVersion());
    byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
    byteBuf.writeByte(packet.getCommand());
    byteBuf.writeInt(bytes.length);
    byteBuf.writeBytes(bytes);
    return byteBuf;
  }

  public static ByteBuf encode(ByteBuf byteBuf, Packet packet) {
    byte[] bytes = Serializer.DEFAULT.serialize(packet);
    // 数据包由五部分组成 魔数、版本号、序列化算法、指令、数据长度、数据内容
    byteBuf.writeInt(MAGIC_NUMS);
    byteBuf.writeByte(packet.getVersion());
    byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
    byteBuf.writeByte(packet.getCommand());
    byteBuf.writeInt(bytes.length);
    byteBuf.writeBytes(bytes);
    return byteBuf;
  }

  /**
   * 解码
   *
   * @param byteBuf 字节容器
   * @return 数据包对象
   */
  public static Packet decode(ByteBuf byteBuf) {
    byteBuf.skipBytes(5);
    // 算法
    byte algorithm = byteBuf.readByte();
    // 指令
    byte command = byteBuf.readByte();
    // 数据长度
    int length = byteBuf.readInt();
    // 数据内容
    byte[] bytes = new byte[length];
    byteBuf.readBytes(bytes);

    Class<? extends Packet> requestType = getRequestType(command);
    Serializer serializer = getSerializer(algorithm);

    if (requestType != null && serializer != null) {
      return serializer.deSerialize(bytes, requestType);
    }
    return null;
  }

  /** @return 获取请求类型 */
  private static Class<? extends Packet> getRequestType(byte command) {
    Command[] values = Command.values();
    for (Command val : values) {
      if (val.getCode().byteValue() == command) {
        return val.getClazz();
      }
    }
    return null;
  }

  private static Serializer getSerializer(byte serialize) {
    return serializerMap.get(serialize);
  }
}
