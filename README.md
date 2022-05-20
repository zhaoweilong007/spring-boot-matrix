# spring-boot-matrix

🎉介绍：
> spring-boot与各种框架整合
> 适合学习在接触新的框架时使用，可以参考本项目和相关博客一起学习

[//]: # ( Path: CHANGELOG.md

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [📌 **项目环境**](#-%E9%A1%B9%E7%9B%AE%E7%8E%AF%E5%A2%83)
- [🔥项目结构](#%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84)
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
  - [SkyWalking](#skywalking)
  - [jenkins+docker+jib实现CI/CD](#jenkinsdockerjib%E5%AE%9E%E7%8E%B0cicd)
  - [prometheus](#prometheus)
  - [ELK](#elk)
- [🎈捐赠](#%E6%8D%90%E8%B5%A0)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## 📌 **项目环境**

- JDK11
- Mysql8.0
- Gradle7.3.3
- Redis5.0
- Idea

> 关于项目文档，可以在我的博客查看，[Java learning](https://zhaoweilong007.github.io/Java-learning)

## 🔥项目结构

### netty

> 基于netty实现简单聊天室

包含以下功能：

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

### grpc

> 基于grpc+protobuf实现RPC调用

**项目地址**:

- [grpc-client](/grpc-client)
- [grpc-interface](/grpc-interface)
- [grpc-server](/grpc-server)

### dubbo

> 基于dubbo+zookeeper实现RPC调用


**项目地址**:

- [dubbo-client](/dubbo-client)
- [dubbo-interface](/dubbo-interface)
- [dubbo-server](/dubbo-server)

**博客地址**：

- [Dubbo入门实践](https://zhaoweilong007.github.io/Java-learning/#/blog/dubbo%E5%BF%AB%E9%80%9F%E5%85%A5%E9%97%A8)

### elastic search

> 基于elastic search实现简单的搜索功能

**项目地址**:

- [elasticSearch](/elasticSearch)

**学习笔记**

- [elasticSearch学习笔记](https://zhaoweilong007.github.io/Java-learning/#/notes/elasticSearch%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0)

### mongoDB

> 基于mongoDB实现简单的增删改查功能

项目地址:[mongodb](/mongodb)

### multiDatasource

> 基于mybatis-plus+dynamic-datasource实现多数据源功能

项目地址：[multidatasource](/multidatasource)

### ShardingSphere

> 基于ShardingSphere实现数据分库分表功能

项目地址：[shardingsphere](/shardingsphere)

### seata

> 基于seata+dubbo实现分布式事务功能

**项目地址**:

- [seata-account-service](/seata-account-service)
- [seata-dubbo-interface](/seata-dubbo-interface)
- [seata-order-service](/seata-order-service)
- [seata-storage-service](/seata-storage-service)

**博客地址**：

- [seate笔记](https://zhaoweilong007.github.io/Java-learning/#/notes/seata)
- [seata实践](https://zhaoweilong007.github.io/Java-learning/#/blog/seata%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1%E4%BD%BF%E7%94%A8)

### rabbitmq

> 基于rabbitmq实现简单的消息队列功能

**项目地址**：

- [rabbitmq-consumer](/rabbitmq-consumer)
- [rabbitmq-provider](/rabbitmq-provider)

### rocketmq

> 基于rocketmq实现简单的消息队列功能

**项目地址**：

- [rocketmq-consumer](/rocketmq-consumer)
- [rocketmq-provider](/rocketmq-provider)

### kafka

> 基于kafka实现简单的消息队列功能

**项目地址**：

- [kafka-consumer](/kafka-consumer)
- [kafka-provider](/kafka-provider)

### SkyWalking

> 基于SkyWalking实现链路追踪功能

- 项目地址：[skywalking](/skywalking)
- 博客地址：[SkyWalking 实战](https://zhaoweilong007.github.io/Java-learning/#/blog/skywalking%E5%AE%9E%E8%B7%B5)

### jenkins+docker+jib实现CI/CD

> Jenkins+docker+jib实现项目自动化构建部署并运行

- 项目地址：[docker](/docker)
- 博客地址：[Jenkins实践](https://zhaoweilong007.github.io/Java-learning/#/blog/Jenkins%E5%AE%9E%E7%8E%B0%E8%87%AA%E5%8A%A8%E5%8C%96%E9%83%A8%E7%BD%B2)

### prometheus

> 使用prometheus+grafana实现监控告警功能

- 项目地址：[prometheus](/prometheus)
- 博客地址：[Prometheus实践](https://zhaoweilong007.github.io/Java-learning/#/blog/prometheus%E5%AE%9E%E8%B7%B5)

### ELK

> 使用elasticSearch+logStash+kibana收集SpringBoot日志

- 项目地址：[elkapp](/elkapp)
- 博客地址：[ELK实践](https://zhaoweilong007.github.io/Java-learning/#/blog/elk%E5%AE%9E%E8%B7%B5)

## 🎈捐赠

如果项目对您有帮助，可以请作者喝杯咖啡

<img src="https://zhaoweilong007.github.io/Java-learning/images/pay.png" width = "250" height = "250" />
