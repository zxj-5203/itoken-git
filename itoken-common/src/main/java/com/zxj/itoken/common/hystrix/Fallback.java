package com.zxj.itoken.common.hystrix;

import com.google.common.collect.Lists;
import com.zxj.itoken.common.constants.HttpConstants;
import com.zxj.itoken.common.dto.BaseResult;
import com.zxj.itoken.common.utils.MapperUtils;

/**
 * 通用的熔断方法
 * @author zxj
 * @date 2020/06/23
 */
public class Fallback {
    /**
     * 502 - 从上游服务器接收到无效的响应
     * @return
     */
    public static String badGateway() {
        BaseResult baseResult = BaseResult.notOk(Lists.newArrayList(new BaseResult.Error(
                String.valueOf(HttpConstants.BAD_GATEWAY.getCode()),
                HttpConstants.BAD_GATEWAY.getMessage())));
        try {
            return MapperUtils.obj2json(baseResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}