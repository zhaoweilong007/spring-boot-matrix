package com.zwl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/6/20 15:25
 **/
@ConfigurationProperties(prefix = XxlJobProperties.PREFIX)
@Configuration
@Data
public class XxlJobProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String PREFIX = "xxl-job";

    /**
     * 是否开启xxl-job
     */
    private Boolean enable;

    /**
     * 调度中心地址
     */
    private String adminAddresses;

    private String accessToken;

    /**
     * 执行器AppName[选填]:执行器心跳注册分组依据,为空则关闭自动注册(xxl-job executor app name)
     */
    private String executorAppName;

    /**
     * 执行器注册[选填]:优先使用该配置作为注册地址,为空时使用内嵌服务 IP:PORT 作为注册地址
     * 从而更灵活的支持容器类型执行器动态IP和动态映射端口问题
     */
    private String executorAddress;


    /**
     * 执行器IP[选填]:默认为空表示自动获取IP,多网卡时可手动设置指定IP,该IP不会绑定Host仅作为通讯实用.
     * 地址信息用于'执行器注册'和'调度中心请求并触发任务'.
     */
    private String executorIp;

    /**
     * 执行器端口号[选填]:小于等于0则自动获取,默认端口为9999,单机部署多个执行器时,注意要配置不同执行器端口.
     */
    private int executorPort;

    /**
     * 执行器运行日志文件存储磁盘路径[选填]:需要对该路径拥有读写权限,为空则使用默认路径.
     */
    private String executorLogPath;

    /**
     * 执行器日志文件保存天数[选填],过期日志自动清理,限制值大于等于3时生效;否则,如-1,关闭自动清理功能.
     */
    private int executorLogRetentionDays;

}
