package com.zxj.itoken.service.sso.service.impl;

import com.zxj.itoken.common.domain.TbSysUser;
import com.zxj.itoken.common.utils.MapperUtils;
import com.zxj.itoken.service.sso.mapper.TbSysUserMapper;
import com.zxj.itoken.service.sso.service.LoginService;
import com.zxj.itoken.service.sso.service.consumer.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * @author zxj
 * @date 2020/06/23
 */
@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private TbSysUserMapper tbSysUserMapper;

    @Override
    public TbSysUser login(String loginCode, String plantPassword) {
        TbSysUser tbSysUser = null;

        // 根据loginCode作为key从Redis中获取value
        String json = redisService.get(loginCode);

        // 缓存中没有数据，直接查数据库，然后添加到缓存中
        if (json == null) {
            Example example = new Example(TbSysUser.class);
            // 封装查询条件：登录账号
            example.createCriteria().andEqualTo("loginCode", loginCode);
            // 根据封装的查询条件，查询数据
            tbSysUser = tbSysUserMapper.selectOneByExample(example);
            // 将页面输入的明文密码 MD5 加密
            String password = DigestUtils.md5DigestAsHex(plantPassword.getBytes());
            // 将加密的明文密码 和 数据库中查询的记录对比；
            if (tbSysUser != null && password.equals(tbSysUser.getPassword())) {
                try {
                    redisService.put(loginCode, MapperUtils.obj2json(tbSysUser), 60*60*24);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 密码正确，则返回用户信息； 否则返回 null；
                return tbSysUser;
            } else {
                return null;  // 密码错误，直接返回null
            }
        }
        // 缓存中有数据，直接返回
        else {
            try {
                tbSysUser = MapperUtils.json2pojo(json, TbSysUser.class);
            } catch (Exception e) {
                // 发生熔断，记录日志
                logger.warn("触发熔断：{}", e.getMessage());
            }
        }
        return tbSysUser;
    }
}
