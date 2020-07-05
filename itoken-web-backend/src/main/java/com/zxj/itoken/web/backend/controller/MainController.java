package com.zxj.itoken.web.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zxj
 * @date 2020/07/01
 */
@Controller
public class MainController {

    @GetMapping(value = "")
    public String main(){
        return "main";
    }

    /**
     * 欢迎页
     * @return
     */
    @GetMapping(value = "welcome")
    public String welcome(){
        return "welcome";
    }
}
