package com.zxj.itoken.web.admin.interceptor;

import com.ctc.wstx.util.StringUtil;
import com.zxj.itoken.common.domain.TbSysUser;
import com.zxj.itoken.common.utils.CookieUtils;
import com.zxj.itoken.common.utils.MapperUtils;
import com.zxj.itoken.common.web.constants.WebConstants;
import com.zxj.itoken.common.web.utils.HttpServletUtils;
import com.zxj.itoken.web.admin.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value(value = "${hosts.sso}")
    private String hosts_sso;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从cookie中获取token
        String token = CookieUtils.getCookieValue(request, WebConstants.SESSION_TOKEN);

        // token为空，肯定没登录，带自己的url跳转到认证中心
        if(StringUtils.isBlank(token)){
            try {
                response.sendRedirect(String.format("%s/login?url=%s", hosts_sso, HttpServletUtils.getFullPath(request)));
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
        TbSysUser tbSysUser = (TbSysUser)session.getAttribute(WebConstants.SESSION_USER);

        // 已登录状态；将用户信息方到request域
        if(tbSysUser != null){
            if(modelAndView != null){
                modelAndView.addObject(WebConstants.SESSION_USER, tbSysUser);
            }
        }
        // 未登录状态
        else{
            String token = CookieUtils.getCookieValue(request, WebConstants.SESSION_TOKEN);
            if(StringUtils.isNotBlank(token)){
                String loginCode = redisService.get(token);
                if(StringUtils.isNotBlank(loginCode)){
                    String json = redisService.get(loginCode);
                    if(StringUtils.isNotBlank(json)){
                        try {
                            // 已登录状态，创建局部会话，将用户信息添加进session
                            tbSysUser = MapperUtils.json2pojo(json,  TbSysUser.class);
                            if(modelAndView != null){
                                modelAndView.addObject(WebConstants.SESSION_USER, tbSysUser);
                            }
                            request.getSession().setAttribute(WebConstants.SESSION_USER, tbSysUser);
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
                response.sendRedirect(String.format("%s/login?url=%s", hosts_sso, HttpServletUtils.getFullPath(request)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}






