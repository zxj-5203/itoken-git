package com.zxj.itoken.web.admin.fallback;

import com.google.common.collect.Lists;
import com.zxj.itoken.common.constants.HttpConstants;
import com.zxj.itoken.common.dto.BaseResult;
import com.zxj.itoken.common.utils.MapperUtils;
import com.zxj.itoken.web.admin.service.AdminService;
import org.springframework.stereotype.Component;

/**
 * @author zxj
 * @date 2020/06/21
 */
@Component
public class AdminServiceFallback implements AdminService {
    /**
     * 熔断方法，需要返回错误信息：code=502, msg="无法连接上游服务器"
     * 熔断，是因为本服务方法连不上上游服务器，前端通过Controller访问这个服务是成功的；
     */
    @Override
    public String login(String loginCode, String password) {
        BaseResult baseResult = BaseResult.notOk(Lists.newArrayList(new BaseResult.Error(
                String.valueOf(HttpConstants.BAD_GATEWAY.getCode()),
                HttpConstants.BAD_GATEWAY.getMessage())));
        try {
            return MapperUtils.obj2json(baseResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;   // 如果出异常，暂时返回null
    }
}

