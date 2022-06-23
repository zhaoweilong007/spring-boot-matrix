package com.zwl.controller;

import cn.hutool.extra.spring.EnableSpringUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/6/23 9:41
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
@EnableSpringUtil
public class JobController {

    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;


    /**
     * 会根据job名称和job参数判断数据库是否存在jobInstance ，不存在新增任务运行，存在则抛出异常
     *
     * @param jobParam 任务参数
     */
    @GetMapping("/run")
    public ResponseEntity<String> runJobWithParam(@RequestParam("param") String jobParam) throws Exception {
        log.info("invoke runJobWithParam....jobParam:{}", jobParam);
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobParam", jobParam)
                .toJobParameters();
        jobLauncher.run(SpringUtil.getBean("demoJob1"), jobParameters);
        return ResponseEntity.ok("job run success");
    }


    @GetMapping("/start")
    public ResponseEntity<String> startJobWithParam(@RequestParam("param") String jobParam) throws Exception {
        log.info("invoke startJobWithParam....jobParam:{}", jobParam);
        jobOperator.start("demoJob1", "jobParam=" + jobParam);
        return ResponseEntity.ok("job run success");
    }


}
