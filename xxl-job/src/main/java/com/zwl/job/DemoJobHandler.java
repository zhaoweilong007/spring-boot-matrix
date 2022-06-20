package com.zwl.job;

import cn.hutool.core.util.RuntimeUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/6/20 17:48
 **/
public class DemoJobHandler extends IJobHandler {

    private static final Logger log = LoggerFactory.getLogger(JobHandlerExecutor.class);


    @Override
    public void execute() throws Exception {

        long jobId = XxlJobHelper.getJobId();
        String jobParam = XxlJobHelper.getJobParam();

        log.info("DemoJobHandler jobId:{}, jobParam:{}", jobId, jobParam);
        XxlJobHelper.log("demoJobHandler jobId:{}, jobParam:{}", jobId, jobParam);

        String exec = RuntimeUtil.execForStr(jobParam);

        XxlJobHelper.handleSuccess(exec);

        XxlJobHelper.log("demoJobHandler exec：{}", exec);
        log.info("demoJobHandler exec:{}", exec);

    }
}
