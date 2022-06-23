package com.zwl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 描述：
 *
 * @author zwl
 * @since 2022/6/22 11:19
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BatchApp.class})
@Slf4j
public class JobTest {


    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Configuration
    static class BatchTestConfig {
        @Autowired
        private Job answerMigrationJob;

        @Bean
        public JobLauncherTestUtils jobLauncherTestUtils() {
            JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
            jobLauncherTestUtils.setJob(answerMigrationJob);
            return jobLauncherTestUtils;
        }
    }


    @DisplayName("first test")
    @Test
    public void test() {
        log.info("test run...");
    }


}
