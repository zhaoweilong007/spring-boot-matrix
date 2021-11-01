package com.zwl.command;

import com.zwl.model.QuitGroupReqPacket;
import io.netty.channel.Channel;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ZhaoWeiLong
 * @since 2021/8/19
 **/
@Slf4j
@Component("quitGroup")
public class QuitGroupCommand implements ConsoleCommand {

  @Override
  public void exec(Scanner scanner, Channel channel) {
    log.info("【退出群聊】,输入群聊id");
    String command = scanner.nextLine();
    QuitGroupReqPacket reqPacket = new QuitGroupReqPacket();
    reqPacket.setGroupId(command);
    channel.writeAndFlush(reqPacket);
  }
}
