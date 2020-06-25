package com.zxj.itoken.web.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zxj
 * @date 2020/06/20
 */
@SpringBootApplication(scanBasePackages = "com.zxj.itoken")
@EnableDiscoveryClient  // 寻找Eureka，注册本项目到Eureka上；
@EnableFeignClients     // 表示这是一个Feign客户端，开启Feign功能；
public class WebAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAdminApplication.class, args);
    }
}
