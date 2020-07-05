package com.zxj.itoken.web.posts.config;

import com.zxj.itoken.web.posts.interceptor.WebPostsInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 重构：改用 WebInterceptorConfiguration
 * @author zxj
 * @date 2020/06/25
 */
@Configuration
public class WebPostsInterceptorConfig implements WebMvcConfigurer {
    @Bean
    WebPostsInterceptor webPostsInterceptor(){
        return new WebPostsInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 在这里如果直接使用new创建WebAdminInterceptor，不会被spring容器管理，
        // 那么在WebAdminInterceptor中就不能通过Autowird注入Bean
        registry.addInterceptor(webPostsInterceptor())
                .addPathPatterns("/**")           // 拦截全路径
                .excludePathPatterns("/static/**");  //  过滤掉静态资源路径
    }
}
