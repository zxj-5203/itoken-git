package com.zxj.itoken.web.posts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zxj
 * @date 2020/06/28
 */
@SpringBootApplication(scanBasePackages = "com.zxj.itoken")
@EnableEurekaClient
@EnableFeignClients
public class WebPostsApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebPostsApplication.class, args);
    }
}
