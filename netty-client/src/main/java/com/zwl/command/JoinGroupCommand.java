package com.zwl.command;

import com.zwl.model.JoinGroupReqPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/19
 */
@Slf4j
@Component("joinGroup")
public class JoinGroupCommand implements ConsoleCommand {

  @Override
  public void exec(Scanner scanner, Channel channel) {
    log.info("【拉人群聊】,输入群聊ID+空格+用户id（多个用户使用逗号分割）");
    String command = scanner.nextLine();
    String[] split = command.split(" ");
    JoinGroupReqPacket reqPacket = new JoinGroupReqPacket();
    reqPacket.setGroupId(split[0]);
    reqPacket.setUserIds(Arrays.asList(split[1].split(",")));
    channel.writeAndFlush(reqPacket);
  }
}
