package com.zxj.itoken.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.internal.EnableZipkinServer;

/**
 * @author zxj
 * @create 2019-09-08
 */
@SpringBootApplication
@EnableEurekaClient   // 表明自己是一个 Eureka Client，是一个服务提供者；
@EnableZipkinServer   // 开启 Zipkin Server 功能
public class ZipKinApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipKinApplication.class, args);
    }
}