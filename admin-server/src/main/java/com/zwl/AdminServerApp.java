package com.zwl;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述：
 *
 * @author zwl
 * @since ${DATE} ${TIME}
 **/
@SpringBootApplication
@EnableAdminServer
public class AdminServerApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApp.class, args);
    }
}