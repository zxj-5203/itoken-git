package com.zxj.itoken.service.redis.controller;

import com.zxj.itoken.service.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxj
 * @date 2020/06/23
 */
@RestController
public class RedisController {
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "put", method = RequestMethod.POST)
    public String put(String key, String value, long seconds){
        redisService.put(key, value, seconds);
        return "ok";
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public String get(String key){
        Object obj = redisService.get(key);
        if(obj != null){
            String json = String.valueOf(obj);
            return json;
        }
        return null;
    }


}
