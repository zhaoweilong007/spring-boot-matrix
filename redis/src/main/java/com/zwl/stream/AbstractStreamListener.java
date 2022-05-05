package com.zwl.stream;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;

/**
 * 描述：stream 消息监听器
 *
 * @author zwl
 * @since 2022/3/10 9:11
 */
public abstract class AbstractStreamListener<M extends AbstractStreamMessage>
    implements StreamListener<String, ObjectRecord<String, M>> {

  @Getter private final String streamKey;

  /** 同一个消费组消息只能消费一次 */
  @Value("spring.application.name")
  @Getter
  @Setter
  protected String group;

  @Setter private RedisTemplate<String, Object> redisTemplate;

  @SneakyThrows
  public AbstractStreamListener() {
    Class<?> aClass = ClassUtil.getTypeArgument(getClass(), 0);
    M m = ((M) ReflectUtil.newInstance(aClass));
    this.streamKey = m.getStreamKey();
  }

  @Override
  public void onMessage(ObjectRecord<String, M> message) {
    this.onMessage(message.getValue());
    // 手动ack
    redisTemplate.opsForStream().acknowledge(group, message);
  }

  public abstract void onMessage(M message);
}
