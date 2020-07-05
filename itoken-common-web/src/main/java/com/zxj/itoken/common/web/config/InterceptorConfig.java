package com.zxj.itoken.common.web.config;

import com.zxj.itoken.common.web.interceptor.ConstantsInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zxj
 * @date 2020/06/24
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public ConstantsInterceptor constantsInterceptor() {
        return new ConstantsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(constantsInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**");
    }
}
