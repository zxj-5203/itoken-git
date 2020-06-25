package com.zxj.itoken.common.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 初始化常量拦截器
 *
 * @author zxj
 * @date 2020/06/24
 */
public class ConstantsInterceptor implements HandlerInterceptor {
    private static final String HOST_CDN = "http://192.168.141.180:81";
    private static final String TEMPLATE_ADMIN_LTE = "adminlte/v2.4.1/AdminLTE-2.4.1";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 默认的false，在请求过来时就拦截了；这里返回true，才能进下一阶段postHandle
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 如果是RestController，MV就是空
        if(modelAndView != null){
            modelAndView.addObject("adminlte", HOST_CDN + "/" + TEMPLATE_ADMIN_LTE);
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
