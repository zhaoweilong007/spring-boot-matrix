package com.zwl.serialize;

import com.alibaba.fastjson.JSON;

/**
 * fastjson序列化
 *
 * @author ZhaoWeiLong
 * @since 2021/7/27
 **/
public class JsonSerializerImpl implements Serializer {

  @Override
  public Byte getSerializerAlgorithm() {
    return SerializerAlgorithm.JSON;
  }

  @Override
  public byte[] serialize(Object obj) {
    return JSON.toJSONBytes(obj);
  }

  @Override
  public <T> T deSerialize(byte[] bytes, Class<T> clazz) {
    return JSON.parseObject(bytes, clazz);
  }
}
