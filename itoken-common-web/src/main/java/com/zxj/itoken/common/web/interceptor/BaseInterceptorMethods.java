package com.zxj.itoken.common.web.interceptor;

import com.zxj.itoken.common.domain.TbSysUser;
import com.zxj.itoken.common.utils.CookieUtils;
import com.zxj.itoken.common.utils.MapperUtils;
import com.zxj.itoken.common.web.constants.WebConstants;
import com.zxj.itoken.common.web.utils.HttpServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
/**
 * 拦截器通用函数
 *
 * @author zxj
 * @date 2020/06/29
 */
@Component
public class BaseInterceptorMethods {

    @Value("${hosts.sso}")
    private static String HOSTS_SSO;

    /**
     * 登录方法拦截
     *
     * @param request
     * @param response
     * @param handler
     * @param url  跳转地址
     * @return
     */
    public static boolean preHandleForLogin(HttpServletRequest request, HttpServletResponse response, Object handler, String url) {
        // 从cookie中获取token
        String token = CookieUtils.getCookieValue(request, WebConstants.SESSION_TOKEN);

        // token为空，肯定没登录，带自己的url跳转到认证中心
        if (StringUtils.isBlank(token)) {
            try {
                // http://localhost:8503/login?url=localhost:8601
                // response.sendRedirect(String.format("%s/login?url=%s", hosts_sso, HttpServletUtils.getFullPath(request)));
                response.sendRedirect(String.format("%s/login?url=%s", HOSTS_SSO, url));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        return true;
    }

    /**
     * 登录方法拦截
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @param tbSysUserJson 登录用户 JSON 对象
     * @param url           跳转地址
     */
    public static void postHandleForLogin(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView, String tbSysUserJson, String url) {
        // 从局部会话 session 中获取用户信息
        HttpSession session = request.getSession();
        TbSysUser tbSysUser = (TbSysUser) session.getAttribute(WebConstants.SESSION_USER);

        // 已登录状态
        if (tbSysUser != null) {
            if (modelAndView != null) {
                modelAndView.addObject(WebConstants.SESSION_USER, tbSysUser);
            }
        }

        // 未登录状态
        else {
            if (StringUtils.isNotBlank(tbSysUserJson)) {
                try {
                    // 已登录状态，创建局部会话，将用户信息添加进session
                    tbSysUser = MapperUtils.json2pojo(tbSysUserJson, TbSysUser.class);
                    if (modelAndView != null) {
                        modelAndView.addObject(WebConstants.SESSION_USER, tbSysUser);
                    }
                    request.getSession().setAttribute(WebConstants.SESSION_USER, tbSysUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 二次确认是否有用户信息
        if (tbSysUser == null) {
            try {
                response.sendRedirect(String.format("%s/login?url=", HOSTS_SSO, url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
