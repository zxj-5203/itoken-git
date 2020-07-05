package com.zxj.itoken.service.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zxj
 * @date 2020/06/16
 */
@SpringBootApplication(scanBasePackages = "com.zxj.itoken")
@EnableEurekaClient
@EnableSwagger2
// 添加包扫描路径：注入自己的mapper和common的mapper
@MapperScan(basePackages = {"com.zxj.itoken.service.admin.mapper", "com.zxj.itoken.common.mapper"})
public class ServiceAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAdminApplication.class, args);
    }
}
