package com.zxj.itoken.service.digiccy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.zxj.itoken.common.mapper", "com.zxj.itoken.service.digiccy.mapper"})
public class ServiceDigiccyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDigiccyApplication.class, args);
    }
}
