package com.zxj.itoken.service.admin.service;


import com.zxj.itoken.common.domain.TbSysUser;

/**
 * @author zxj
 * @date 2020/06/18
 */
public interface AdminService {

    /**
     * 注册
     * @param tbSysUser
     */
    public void register(TbSysUser tbSysUser);

    /**
     * 注册
     * @param loginCode 登录账号
     * @param plantPassword 明文密码
     */
    public TbSysUser login(String loginCode, String plantPassword);
}
