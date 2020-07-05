package com.zxj.itoken.service.admin.mapper;

import com.zxj.itoken.common.domain.TbSysUser;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.MyMapper;

// 扩展Mapper
@Repository
public interface TbSysUserExtendMapper extends MyMapper<TbSysUser> {
}