package com.zxj.itoken.service.sso.service;

import com.zxj.itoken.common.domain.TbSysUser;

/**
 * @author zxj
 * @date 2020/06/23
 */
public interface LoginService {
    /**
     * 登录
     * @param loginCode
     * @param plantPassword
     * @return
     */
    public TbSysUser login(String loginCode, String plantPassword);
}
