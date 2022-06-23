package com.zwl.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述： spring batch job config
 *
 * @author zwl
 * @since 2022/6/21 15:53
 **/
@Configuration
@EnableBatchProcessing(modular = true)
@Slf4j
public class SpringBatchJobConfig {


    @Bean
    public GenericApplicationContextFactory demoJobContext() {
        return new GenericApplicationContextFactory(DemoBatchConfig.class);
    }

    @Bean
    public GenericApplicationContextFactory answerMigrationJobContext() {
        return new GenericApplicationContextFactory(BatchMigrationConfiguration.class);
    }


}
