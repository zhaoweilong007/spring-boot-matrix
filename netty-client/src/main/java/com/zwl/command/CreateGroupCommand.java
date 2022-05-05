package com.zwl.command;

import com.zwl.model.CreateGroupRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 创建群聊指令
 *
 * @author ZhaoWeiLong
 * @since 2021/8/19
 */
@Slf4j
@Component("createGroup")
public class CreateGroupCommand implements ConsoleCommand {

  @Override
  public void exec(Scanner scanner, Channel channel) {
    log.info("【创建群聊】输入userId列表，以逗号分割");
    String command = scanner.nextLine();
    CreateGroupRequestPacket packet = new CreateGroupRequestPacket();
    packet.setUserIds(Stream.of(command.split(",")).collect(Collectors.toList()));
    channel.writeAndFlush(packet);
  }
}
