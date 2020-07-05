package com.zxj.itoken.common.mapper;

import com.zxj.itoken.common.domain.TbDigiccyExchange;
import com.zxj.itoken.common.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.MyMapper;

@CacheNamespace(implementation = RedisCache.class)
public interface TbDigiccyExchangeMapper extends MyMapper<TbDigiccyExchange> {
}