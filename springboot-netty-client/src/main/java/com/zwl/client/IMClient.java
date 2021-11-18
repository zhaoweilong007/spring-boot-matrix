package com.zwl.client;

import com.zwl.command.ConsoleCommandManager;
import com.zwl.command.LoginCommand;
import com.zwl.handler.*;
import com.zwl.utils.LogUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * IM聊天室客户端
 *
 * @author ZhaoWeiLong
 * @since 2021/7/29
 */
@Slf4j
@Component
public class IMClient implements ApplicationRunner {

  private static final ExecutorService EXECUTORS = Executors.newSingleThreadExecutor();

  private static final CountDownLatch countDownLatch = new CountDownLatch(1);

  /**
   * 连接
   *
   * @param bootstrap 启动器
   * @param retry 重试次数
   */
  public static void connect(final Bootstrap bootstrap, final int retry) {
    bootstrap
        .connect("127.0.0.1", 8000)
        .addListener(
            future -> {
              if (future.isSuccess()) {
                log.info("客户端连接成功....");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
              } else {
                if (retry > 0) {
                  connect(bootstrap, retry - 1);
                } else {
                  log.error("连接服务器失败，超过重试次数");
                }
              }
            });
  }

  /**
   * 开始读取用户输入
   *
   * @param channel
   */
  private static void startConsoleThread(Channel channel) {
    LoginCommand loginCommand = new LoginCommand();
    ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
    Scanner scanner = new Scanner(System.in);
    EXECUTORS.execute(
        () -> {
          while (!EXECUTORS.isShutdown()) {
            if (LogUtils.hasLogin(channel)) {
              try {
                countDownLatch.await();
                consoleCommandManager.exec(scanner, channel);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            } else {
              loginCommand.exec(scanner, channel);
              countDownLatch.countDown();
            }
          }
        });
  }

  @Override
  public void run(ApplicationArguments args) {

    NioEventLoopGroup loopGroup = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    Bootstrap boot =
        bootstrap
            .group(loopGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .handler(
                new ChannelInitializer<SocketChannel>() {
                  @Override
                  protected void initChannel(SocketChannel socketChannel) {
                    // 设置拆包，基于长度符的拆包器，根据自定义协议
                    socketChannel.pipeline().addLast(new IMIdleStateHandler());
                    socketChannel.pipeline().addLast(new UnPackDeCoder());
                    socketChannel.pipeline().addLast(PacketCodecHandler.INSTANCE);
                    socketChannel.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                    socketChannel.pipeline().addLast(HeartBeatRespHandler.INSTANCE);
                    socketChannel.pipeline().addLast(LoginRespHandler.INSTANCE);
                    socketChannel.pipeline().addLast(HeartBeatTimerHandler.INSTANCE);
                    socketChannel.pipeline().addLast(IMHandler.INSTANCE);
                  }
                });
    connect(boot, 3);
  }
}
