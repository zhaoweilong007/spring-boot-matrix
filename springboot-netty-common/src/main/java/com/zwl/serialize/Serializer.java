package com.zwl.serialize;

/**
 * 序列化接口
 *
 * @author ZhaoWeiLong
 * @since 2021/7/27
 **/
public interface Serializer {


  Serializer DEFAULT = new JsonSerializerImpl();

  Byte getSerializerAlgorithm();

  /**
   * 序列化
   *
   * @param obj 对象
   * @return 二进制数据
   */
  byte[] serialize(Object obj);


  /**
   * 反序列化
   *
   * @param bytes 二进制数据
   * @param clazz 类对象
   * @return 对象
   */
  <T> T deSerialize(byte[] bytes, Class<T> clazz);

}
