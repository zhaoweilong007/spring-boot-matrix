package com.zwl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 描述：
 *
 * @author zwl
 * @since 2022/3/4 17:45
 **/
@SpringBootApplication
@EnableScheduling
public class RedisApp {

    public static void main(String[] args) {
        SpringApplication.run(RedisApp.class, args);
    }
}
