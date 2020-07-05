package com.zxj.itoken.service.admin.service;


import com.zxj.itoken.common.domain.BaseDomain;
import com.zxj.itoken.common.domain.TbSysUser;
import com.zxj.itoken.common.mapper.TbSysUserMapper;
import com.zxj.itoken.common.service.BaseService;
import com.zxj.itoken.common.service.impl.BaseServiceImpl;

/**
 * @author zxj
 * @date 2020/06/18
 */
// 接口继承通用 CURD 接口，定义泛型
public interface AdminService extends BaseService<TbSysUser>{

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
