package com.zxj.itoken.service.posts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zxj
 * @date 2020/06/26
 */
@SpringBootApplication(scanBasePackages = "com.zxj.itoken")
@EnableEurekaClient
@EnableSwagger2
@MapperScan(basePackages = {"com.zxj.itoken.common.mapper", "com.zxj.itoken.service.posts.mapper"})
public class ServicePostsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePostsApplication.class, args);
    }
}
