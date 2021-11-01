package com.zwl.server;

import com.zwl.handler.AuthHandler;
import com.zwl.handler.HeartBeatRequestHandler;
import com.zwl.handler.HeartBeatRespHandler;
import com.zwl.handler.HeartBeatTimerHandler;
import com.zwl.handler.IMHandler;
import com.zwl.handler.IMIdleStateHandler;
import com.zwl.handler.LoginRequestHandler;
import com.zwl.handler.PacketCodecHandler;
import com.zwl.handler.UnPackDeCoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * IM聊天室服务端
 *
 * @author ZhaoWeiLong
 * @since 2021/7/29
 **/
@Slf4j
@Component
public class IMServer implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workGroup = new NioEventLoopGroup();

    ServerBootstrap serverBootstrap = new ServerBootstrap();

    try {
      serverBootstrap
          .group(bossGroup, workGroup)
          .localAddress(new InetSocketAddress(8000))
          .channel(NioServerSocketChannel.class)
          .childOption(ChannelOption.SO_KEEPALIVE, true)
          .childOption(ChannelOption.TCP_NODELAY, true)
          //注册初始化
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
              socketChannel.pipeline().addLast(new IMIdleStateHandler());
              socketChannel.pipeline().addLast(new UnPackDeCoder());
              socketChannel.pipeline().addLast(PacketCodecHandler.INSTANCE);
              socketChannel.pipeline().addLast(LoginRequestHandler.INSTANCE);
              socketChannel.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
              socketChannel.pipeline().addLast(HeartBeatRespHandler.INSTANCE);
              socketChannel.pipeline().addLast(HeartBeatTimerHandler.INSTANCE);
              socketChannel.pipeline().addLast(AuthHandler.INSTANCE);
              socketChannel.pipeline().addLast(IMHandler.INSTANCE);
            }
          });

      ChannelFuture channelFuture = serverBootstrap.bind().sync();
      log.info("{}服务器启动成功 监听端口：{}", IMServer.class.getName(),
          channelFuture.channel().localAddress());
      ChannelFuture closeFuture = channelFuture.channel().closeFuture();
      closeFuture.sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      workGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }

  }
}
