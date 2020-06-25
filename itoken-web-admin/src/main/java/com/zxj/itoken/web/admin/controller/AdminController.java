package com.zxj.itoken.web.admin.controller;

import com.zxj.itoken.web.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zxj
 * @date 2020/06/20
 */
@Controller   // 做页面跳转，不是rest
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 跳转登录页（sso 之后废弃，使用下面代替）
     * @return
     */
    @RequestMapping(value = {"", "login"}, method = RequestMethod.GET)
    public String login(){
        String json = adminService.login("", "");
        System.out.println(json);
        return "index";
    }

    /**
     * 跳转首页
     * @return
     */
    @GetMapping(value = {"", "index"})
    public String index(){
        return "index";
    }
}
