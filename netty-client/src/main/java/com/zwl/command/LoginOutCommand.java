package com.zwl.command;

import com.zwl.model.LoginOutRequestPacket;
import com.zwl.utils.LogUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * 登出指令
 *
 * @author ZhaoWeiLong
 * @since 2021/8/19
 */
@Slf4j
@Component("logout")
public class LoginOutCommand implements ConsoleCommand {

  @Override
  public void exec(Scanner scanner, Channel channel) {
    log.info("【退出系统】,输入Y/N确认");
    String command = scanner.nextLine();
    if ("y".equalsIgnoreCase(command)) {
      LoginOutRequestPacket packet = new LoginOutRequestPacket();
      packet.setUserId(LogUtils.getSession(channel).getUserId());
      channel.writeAndFlush(packet);
    } else if ("n".equalsIgnoreCase(command)) {
      log.info("已取消");
    } else {
      log.warn("输入指令错误");
    }
  }
}
