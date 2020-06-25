package com.zxj.itoken.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author zxj
 * @create 2019-09-08
 */
@SpringBootApplication
@EnableEurekaClient   // 表明自己是一个 Eureka Client，是一个服务提供者；
@EnableAdminServer    // 开启 Admin 功能
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
