package com.zwl.command;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

/**
 * 指令管理器
 *
 * @author ZhaoWeiLong
 * @since 2021/8/19
 */
@Slf4j
@Component
public class ConsoleCommandManager {

  @Autowired private Map<String, ConsoleCommand> consoleCommandMap;

  public void exec(Scanner scanner, Channel channel) {
    log.info("请输入控制指令:{}", consoleCommandMap.keySet());
    String next = scanner.nextLine();
    ConsoleCommand consoleCommand = consoleCommandMap.get(next);
    if (consoleCommand == null) {
      log.warn("你输入的指令无效！");
      return;
    }
    consoleCommand.exec(scanner, channel);
  }
}
