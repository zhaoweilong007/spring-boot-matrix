# spring-boot-matrix

spring-boot与各种框架整合

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [spring-boot-matrix](#spring-boot-matrix)
    - [netty](#netty)
    - [grpc](#grpc)
    - [dubbo](#dubbo)
    - [elastic search](#elastic-search)
    - [mongoDB](#mongodb)
    - [multiDatasource](#multidatasource)
    - [ShardingSphere](#shardingsphere)
    - [seata](#seata)
    - [rabbitmq](#rabbitmq)
    - [rocketmq](#rocketmq)
    - [kafka](#kafka)
    - [SkyWalking](#SkyWalking)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

**项目环境**

- JDK11
- Mysql8.0
- Gradle7.3.3
- Redis5.0
- Idea

> 关于项目文档，可以在我的博客查看，[Java learning](https://zhaoweilong007.github.io/Java-learning)

## netty

> 基于netty实现简单聊天室

- 自定义协议
- 自定义协议编解码
- 根据协议使用拆包器
- 拒绝非本协议连接
- 心跳检测机制
- 合并handler和并行handler
- 登录退出
- 单聊消息
- 群聊消息
- 创建群聊
- 拉人群聊
- 列出群聊
- 退出群聊

## grpc

> 基于grpc+protobuf实现RPC调用

## dubbo

> 基于dubbo+zookeeper实现RPC调用

## elastic search

> 基于elastic search实现简单的搜索功能

## mongoDB

> 基于mongoDB实现简单的增删改查功能

## multiDatasource

> 基于mybatis-plus+dynamic-datasource实现多数据源功能

## ShardingSphere

> 基于ShardingSphere实现数据分库分表功能

## seata

> 基于seata实现分布式事务功能

## rabbitmq

> 基于rabbitmq实现简单的消息队列功能

## rocketmq

> 基于rocketmq实现简单的消息队列功能

## kafka

> 基于kafka实现简单的消息队列功能

## SkyWalking

> 基于SkyWalking实现链路追踪功能