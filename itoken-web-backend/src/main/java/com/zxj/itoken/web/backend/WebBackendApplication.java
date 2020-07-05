package com.zxj.itoken.web.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zxj
 * @date 2020/07/01
 */
@SpringBootApplication(scanBasePackages = "com.zxj.itoken")
@EnableDiscoveryClient
@EnableFeignClients
public class WebBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebBackendApplication.class, args);
    }
}
