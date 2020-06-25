package com.zxj.itoken.service.admin.controller;

import com.google.common.collect.Lists;
import com.zxj.itoken.common.domain.TbSysUser;
import com.zxj.itoken.common.dto.BaseResult;
import com.zxj.itoken.service.admin.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxj
 * @date 2020/06/20
 */
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 登录
     * @param loginCode
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public BaseResult login(String loginCode, String password){
        // 检查登录信息
        BaseResult baseResult = checkLogin(loginCode, password);
        if(baseResult != null){
            return baseResult;
        }
        // 登录业务
        TbSysUser tbSysUser = adminService.login(loginCode, password);
        // 登录成功
        if(tbSysUser != null){
            return BaseResult.ok(tbSysUser);
        }
        // 登录失败
        else {
            return BaseResult.notOk(Lists.newArrayList(new BaseResult.Error("", "登录失败")));
        }
    }

    /**
     * 检查登录
     * @param loginCode
     * @param password
     * @return
     */
    public BaseResult checkLogin(String loginCode, String password){
        BaseResult baseResult = null;

        if(StringUtils.isBlank(loginCode) || StringUtils.isBlank(password)){
            baseResult = BaseResult.notOk(Lists.newArrayList(
                    new BaseResult.Error("loginCode", "登录账户不能为空"),
                    new BaseResult.Error("password", "密码不能为空")
            ));
        }

        return baseResult;
    }
}
