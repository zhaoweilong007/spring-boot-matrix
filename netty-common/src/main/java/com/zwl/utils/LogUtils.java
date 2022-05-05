package com.zwl.utils;

import com.zwl.model.Attributes;
import com.zwl.model.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登录工具类
 *
 * @author ZhaoWeiLong
 * @since 2021/8/16
 */
@Slf4j
public class LogUtils {

  private static final Map<String, Channel> USER_ID_CHANNEL_MAP = new ConcurrentHashMap<>();
  private static final Map<String, ChannelGroup> GROUP_MAP = new ConcurrentHashMap<>();

  public static void putGroupMap(String groupId, ChannelGroup channelGroup) {
    GROUP_MAP.put(groupId, channelGroup);
  }

  public static ChannelGroup getGroupMap(String groupId) {
    return GROUP_MAP.get(groupId);
  }

  public static Boolean removeGroup(String groupId, Channel channel) {
    ChannelGroup groupMap = getGroupMap(groupId);
    if (groupMap == null) {
      return false;
    }
    return groupMap.remove(channel);
  }

  public static void bindSession(Session session, Channel channel) {
    USER_ID_CHANNEL_MAP.put(session.getUserId(), channel);
    channel.attr(Attributes.SESSION).set(session);
  }

  public static Session getSession(Channel channel) {
    return channel.attr(Attributes.SESSION).get();
  }

  public static Channel getChannel(String userId) {
    return USER_ID_CHANNEL_MAP.get(userId);
  }

  public static void unBindSession(Channel channel) {
    if (hasSession(channel)) {
      USER_ID_CHANNEL_MAP.remove(getSession(channel).getUserId());
      channel.attr(Attributes.SESSION).set(null);
    }
  }

  /**
   * 是否已登录
   *
   * @param channel
   * @return
   */
  public static Boolean hasSession(Channel channel) {
    return channel.hasAttr(Attributes.SESSION);
  }

  public static Boolean hasLogin(Channel channel) {
    return channel.hasAttr(Attributes.LOGIN);
  }

  /**
   * 标记为登录
   *
   * @param channel
   */
  public static void markAsLogin(Channel channel) {
    channel.attr(Attributes.LOGIN).getAndSet(true);
  }
}
