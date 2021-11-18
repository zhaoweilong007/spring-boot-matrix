package com.zwl.command;

import com.zwl.model.MessageRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * 发送消息给用户
 *
 * @author ZhaoWeiLong
 * @since 2021/8/19
 */
@Slf4j
@Component("sendToUser")
public class SendToUserCommand implements ConsoleCommand {

  @Override
  public void exec(Scanner scanner, Channel channel) {
    log.info("【发送消息】,消息格式，userId+空格+消息");
    String command = scanner.nextLine();
    String[] str = command.split(" ");
    MessageRequestPacket messageRequestPacket = new MessageRequestPacket(str[0], str[1]);
    channel.writeAndFlush(messageRequestPacket);
  }
}
