package com.zwl.job;

import cn.hutool.core.util.RuntimeUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 描述：可以照着官方示例敲一遍
 *
 * @author zwl
 * @since 2022/6/20 16:30
 **/
@Component
@Slf4j
public class JobHandlerExecutor {


    /**
     * 简单示例
     */
    @XxlJob(value = "simpleJobHandler", init = "init", destroy = "destroy")
    public void jobHandlerExecutor() {
        XxlJobHelper.log("simpleJobHandler  run...{}", Thread.currentThread().getName());

        for (int i = 0; i < 3; i++) {
            log.info("beat at:{}", i);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        XxlJobHelper.log("simpleJobHandler end...");
    }


    /**
     * 分片广播示例
     */
    @XxlJob(value = "shardingJobHandler")
    public void shardingJobHandler() {
        log.info("shardingJobHandler  run...{}", Thread.currentThread().getName());
        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();

        XxlJobHelper.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);
        // 业务逻辑
        for (int i = 0; i < shardTotal; i++) {
            if (i == shardIndex) {
                XxlJobHelper.log("第 {} 片, 命中分片开始处理", i);
            } else {
                XxlJobHelper.log("第 {} 片, 忽略", i);
            }
        }
    }


    /**
     * 命令行参数示例
     */
    @XxlJob(value = "commandJobHandler")
    public void commandJobHandler() {
        XxlJobHelper.log("commandJobHandler  run...{}", Thread.currentThread().getName());
        String jobParam = XxlJobHelper.getJobParam();
        XxlJobHelper.log("commandJobHandler param:{}", jobParam);
        String str = RuntimeUtil.execForStr(jobParam);
        XxlJobHelper.log("commandJobHandler result:{}", str);
    }


    private void init() {
        log.info("init...");
    }

    private void destroy() {
        log.info("destroy...");
    }

}
