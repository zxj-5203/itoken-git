package com.zxj.itoken.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author zxj
 * @create 2019-09-08
 */
@SpringBootApplication
@EnableEurekaClient   // 表明自己是一个 Eureka Client，是一个服务提供者；
@EnableZuulProxy      // 开启 Zuul 功能；
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}