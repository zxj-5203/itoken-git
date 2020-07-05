package com.zxj.itoken.common.web.service;

import com.zxj.itoken.common.hystrix.Fallback;

/**
 * 通用服务消费者接口
 *
 * @author zxj
 * @date 2020/06/29
 */
public interface BaseClientService {
    default String page(int pageNum, int pageSize, String domainJson) {
        return Fallback.badGateway();
    }
}
