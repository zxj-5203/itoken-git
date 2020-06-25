package com.zxj.itoken.service.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zxj
 * @date 2020/06/23
 */
@SpringBootApplication(scanBasePackages = "com.zxj.itoken")
@EnableEurekaClient
@EnableDiscoveryClient   // 是服务提供者的同时，还需要作为服务消费者从Redis拿数据
@EnableFeignClients      // 打来Feign客户端，调用远程服务
@MapperScan(basePackages = "com.zxj.itoken.service.sso.mapper")
public class ServiceSSOApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSSOApplication.class, args);
    }
}
