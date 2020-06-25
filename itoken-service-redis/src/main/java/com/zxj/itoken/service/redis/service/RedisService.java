package com.zxj.itoken.service.redis.service;

/**
 * @author zxj
 * @date 2020/06/23
 */

public interface RedisService {

    /**
     * 添加缓存
     * @param key
     * @param value
     * @param seconds 缓存超时时间
     */
    public void put(String key, Object value, long seconds);

    /**
     * 根据指定key从缓存中获取数据
     * @param key
     * @return
     */
    public Object get(String key);
}
