package com.zxj.itoken.web.admin.interceptor;

import com.ctc.wstx.util.StringUtil;
import com.zxj.itoken.common.domain.TbSysUser;
import com.zxj.itoken.common.utils.CookieUtils;
import com.zxj.itoken.common.utils.MapperUtils;
import com.zxj.itoken.web.admin.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author zxj
 * @date 2020/06/25
 */
public class WebAdminInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从cookie中获取token
        String token = CookieUtils.getCookieValue(request, "token");

        // token为空，肯定没登录，带自己的url跳转到认证中心
        if(StringUtils.isBlank(token)){
            try {
                response.sendRedirect("http://localhost:8503/login?url=localhost:8601");
            }catch (IOException e){
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 从局部会话 session 中获取用户信息
        HttpSession session = request.getSession();
        TbSysUser tbSysUser = (TbSysUser)session.getAttribute("tbSysUser");

        // 已登录状态；将用户信息方到request域
        if(tbSysUser != null){
            if(modelAndView != null){
                modelAndView.addObject("tbSysUser", tbSysUser);
            }
        }
        // 未登录状态
        else{
            String token = CookieUtils.getCookieValue(request, "token");
            if(StringUtils.isNotBlank(token)){
                String loginCode = redisService.get(token);
                if(StringUtils.isNotBlank(loginCode)){
                    String json = redisService.get(loginCode);
                    if(StringUtils.isNotBlank(json)){
                        try {
                            // 已登录状态，创建局部会话，将用户信息添加进session
                            tbSysUser = MapperUtils.json2pojo(json,  TbSysUser.class);
                            request.getSession().setAttribute("tbSysUser", tbSysUser);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        // 二次确认是否有用户信息
        if(tbSysUser == null){
            try {
                response.sendRedirect("http://localhost:8503/login?url=localhost:8601");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}






