package com.zxj.itoken.service.sso.controller;

import com.zxj.itoken.common.domain.TbSysUser;
import com.zxj.itoken.common.utils.CookieUtils;
import com.zxj.itoken.common.utils.MapperUtils;
import com.zxj.itoken.service.sso.service.LoginService;
import com.zxj.itoken.service.sso.service.consumer.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author zxj
 * @date 2020/06/23
 */
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisService redisService;

    /**
     * 跳转登录页
     *
     * @return
     */
    @GetMapping(value = "login")
    public String login(HttpServletRequest request, Model model,
                        @RequestParam(required = false) String url) {
        String token = CookieUtils.getCookieValue(request, "token");
        // token 不为空，可能已登录
        if (StringUtils.isNotBlank(token)) {
            // 根据token获取loginCode
            String loginCode = redisService.get(token);
            if (StringUtils.isNotBlank(loginCode)) {
                // 根据loginCode获取用户信息json串
                String json = redisService.get(loginCode);
                if (StringUtils.isNotBlank(json)) {
                    try {
                        // json转实体信息
                        TbSysUser tbSysUser = MapperUtils.json2pojo(json, TbSysUser.class);
                        // 都不为空，说明用户已登录；
                        if (tbSysUser != null) {
                            // 判断是直接请求的sso系统，还是其他系统跳转过来
                            if (StringUtils.isNotBlank(url)) {
                                // 如果是从其他系统过来的，已登录直接重定向回去；
                                return "redirect:" + url;
                            }
                        }
                        // 将登陆信息返回登录页
                        model.addAttribute("tbSysUser", tbSysUser);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        if (StringUtils.isNotBlank(url)) {
            model.addAttribute("url", url);
        }

        return "login";
    }

    /**
     * 登录业务
     *
     * @param loginCode
     * @param password
     * @return
     */
    @PostMapping(value = "login")
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        // Model model,  // 直接跳转使用这个传参数
                        RedirectAttributes redirectAttributes, // 重定向使用这个传递参数
                        @RequestParam(required = true) String loginCode,
                        @RequestParam(required = true) String password,
                        @RequestParam(required = false) String url) {
        TbSysUser tbSysUser = loginService.login(loginCode, password);

        // 登录失败
        if (tbSysUser == null) {
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误，请重新输入！");
        }
        // 登录成功
        else {
            // 生成一个随机数作为token
            String token = UUID.randomUUID().toString();
            String result = redisService.put(token, loginCode, 60 * 60 * 24);
            // 将token放入缓存成功
            if (StringUtils.isNotBlank(result) && "ok".equals(result)) {
                CookieUtils.setCookie(request, response, "token", token, 60 * 60 * 24);
                if (StringUtils.isNotBlank(url)) {
                    return "redirect:" + url;
                }
            }
            // 熔断处理
            else {
                redirectAttributes.addFlashAttribute("message", "服务器异常，请稍后再试！");
            }
        }

        // 重定向到login，是因为要调用"跳转登录"接口
        return "redirect:/login";
    }

    /**
     * 单点注销
     *
     * @param request
     * @param response
     * @param model
     * @param url
     * @return
     */
    @GetMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model,
                         @RequestParam(required = false) String url) {
        try {
            // 直接删除cookie即可
            CookieUtils.deleteCookie(request, response, "token");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 注销时，可能不是在认证中心里注销的，从别的系统注销过来的，再次登录，还要跳回别的系统，所以需要别的系统的url
        return login(request, model, url);
    }
}













