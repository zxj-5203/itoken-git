package com.zxj.itoken.web.digiccy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zxj
 * @date 2020/07/05
 */
@SpringBootApplication(scanBasePackages = "com.zxj.itoken")
@EnableDiscoveryClient
@EnableFeignClients
public class WebDigiccyApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebDigiccyApplication.class, args);
    }
}