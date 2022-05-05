package com.zwl.handler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.zwl.model.LoginRequestPacket;
import com.zwl.model.LoginRespPacket;
import com.zwl.model.Session;
import com.zwl.utils.LogUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 登录请求处理器
 *
 * @author ZhaoWeiLong
 * @since 2021/8/17
 */
@Slf4j
@Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

  public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

  private static final Map<String, String> USER_MAP =
      new HashMap<>() {
        {
          put("admin", "admin");
          put("root", "root");
        }
      };

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
    LoginRespPacket loginRespPacket = new LoginRespPacket();
    String date = new DateTime().toString(DatePattern.NORM_DATETIME_PATTERN);
    if (valid(loginRequestPacket)) {
      String uuid = IdUtil.randomUUID();
      String userId = uuid.substring(0, uuid.indexOf("-"));
      LogUtils.bindSession(new Session(userId, loginRequestPacket.getUserName()), ctx.channel());
      log.info("{}于{}登录系统{}", loginRequestPacket.getUserName(), date, "成功");
      String message =
          StrUtil.format(
              "😀登录成功♥，【{}】你好，UserId:【{}】，当前时间：{}", loginRequestPacket.getUserName(), userId, date);
      loginRespPacket.setSuccess(true);
      loginRespPacket.setMsg(message);
      loginRespPacket.setUserId(userId);
    } else {
      log.info("{}于{}登录系统{}", loginRequestPacket.getUserName(), date, "失败");
      loginRespPacket.setSuccess(false);
      loginRespPacket.setMsg("登录失败！请检查你的用户名密码");
    }
    // 缩短时间传播路径
    ctx.writeAndFlush(loginRespPacket);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    LogUtils.unBindSession(ctx.channel());
    super.channelInactive(ctx);
  }

  public Boolean valid(LoginRequestPacket requestPacket) {
    String pwd = USER_MAP.get(requestPacket.getUserName());
    return Objects.equals(requestPacket.getPassword(), pwd);
  }
}
