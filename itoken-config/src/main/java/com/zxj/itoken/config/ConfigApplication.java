package com.zxj.itoken.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author zxj
 * @create 2019-09-08
 */
@SpringBootApplication
@EnableConfigServer   // 开启配置服务器功能
@EnableEurekaClient   // 表明自己是一个 Eureka Client，是一个服务提供者；
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }
}