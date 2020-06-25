package com.zxj.itoken.service.admin.service.impl;

import com.zxj.itoken.common.domain.TbSysUser;
import com.zxj.itoken.service.admin.mapper.TbSysUserMapper;
import com.zxj.itoken.service.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * @author zxj
 * @date 2020/06/18
 */
@Service
@Transactional(readOnly = true)   // 只读
public class AdminServiceImpl implements AdminService {
    @Autowired
    private TbSysUserMapper tbSysUserMapper;

    @Override
    @Transactional(readOnly = false)  // 写数据的方法，只读属性设置false，覆盖类上的只读设置；
    public void register(TbSysUser tbSysUser) {
        tbSysUser.setPassword(DigestUtils.md5DigestAsHex(tbSysUser.getPassword().getBytes()));
        tbSysUserMapper.insert(tbSysUser);
    }

    @Override
    public TbSysUser login(String loginCode, String plantPassword) {
        Example example = new Example(TbSysUser.class);
        // 封装查询条件：登录账号
        example.createCriteria().andEqualTo("loginCode", loginCode);
        // 根据封装的查询条件，查询数据
        TbSysUser tbSysUser = tbSysUserMapper.selectOneByExample(example);
        // 将页面输入的明文密码 MD5 加密
        String password = DigestUtils.md5DigestAsHex(plantPassword.getBytes());
        // 将加密的明文密码 和 数据库中查询的记录对比；
        if(password.equals(tbSysUser.getPassword())){
            // 密码正确，则返回用户信息； 否则返回 null；
            return tbSysUser;
        }
        return null;
    }
}
