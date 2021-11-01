package com.zwl.command;

import com.zwl.model.LoginRequestPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import java.util.Scanner;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/20
 **/
@Slf4j
public class LoginCommand implements ConsoleCommand {

  @Override
  public void exec(Scanner scanner, Channel channel) {
    try {
      login(channel);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * 登录
   *
   * @param channel
   * @throws Exception
   */
  private void login(Channel channel) throws Exception {
    Scanner scanner = new Scanner(System.in);
    log.info("请输入账号登录系统。。。");
    String username = scanner.nextLine();
    log.info("请输入密码。。。");
    String pwd = scanner.nextLine();
    LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
    loginRequestPacket.setUserId(UUID.randomUUID().toString().replace("-", ""));
    loginRequestPacket.setUserName(username);
    loginRequestPacket.setPassword(pwd);
    ChannelFuture channelFuture = channel
        .writeAndFlush(loginRequestPacket);
    channelFuture.sync();
    if (!channelFuture.isSuccess()) {
      login(channel);
    }
  }
}
