package com.zxj.itoken.web.backend.fallback;

import com.zxj.itoken.common.hystrix.Fallback;
import com.zxj.itoken.web.backend.service.RedisService;
import org.springframework.stereotype.Component;

/**
 * @author zxj
 * @date 2020/06/23
 */
@Component
public class RedisServiceFallback implements RedisService {

    @Override
    public String put(String key, String value, long seconds) {
         return Fallback.badGateway();
//        return null;
    }

    @Override
    public String get(String key) {
         return Fallback.badGateway();
//        return null;
    }
}
