package com.zxj.itoken.common.mapper;

import com.zxj.itoken.common.domain.TbPostsPost;
import com.zxj.itoken.common.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.MyMapper;

// 指定缓存方案的实现；
@CacheNamespace(implementation = RedisCache.class)
public interface TbPostsPostMapper extends MyMapper<TbPostsPost> {
}