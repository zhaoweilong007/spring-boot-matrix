package com.zwl.command;

import io.netty.channel.Channel;
import java.util.Scanner;

/**
 * 控制台指令
 * @author ZhaoWeiLong
 * @since 2021/8/19
 **/
public interface ConsoleCommand {

  void exec(Scanner scanner, Channel channel);

}
