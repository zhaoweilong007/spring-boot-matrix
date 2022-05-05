package com.zwl.pubsub;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.Topic;

/**
 * 描述： pub/sub 监听器
 *
 * @author zwl
 * @since 2022/3/10 9:11
 */
public abstract class AbstractChannelListener<M extends AbstractChannelMessage>
    implements MessageListener {

  @Getter private String channel;

  private Class<?> type;

  public AbstractChannelListener() {
    Class<?> aClass = ClassUtil.getTypeArgument(getClass(), 0);
    this.type = aClass;
    M m = ((M) ReflectUtil.newInstance(aClass));
    this.channel = m.getChannel();
  }

  public abstract Topic getTopic();

  protected Topic channelTopic() {
    return new ChannelTopic(channel);
  }

  protected Topic patterTopic() {
    return new PatternTopic(channel);
  }

  @Override
  @SneakyThrows
  public void onMessage(Message message, byte[] pattern) {
    M m = ((M) JSON.parseObject(message.getBody(), type));
    this.onMessage(m);
  }

  public abstract void onMessage(M message);
}
