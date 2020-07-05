package com.zxj.itoken.web.admin.service;

import com.zxj.itoken.common.web.service.BaseClientService;
import com.zxj.itoken.web.admin.fallback.AdminServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zxj
 * @date 2020/06/21
 */
@FeignClient(value = "itoken-service-admin", fallback = AdminServiceFallback.class)
public interface AdminService extends BaseClientService {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "loginCode") String loginCode,
                        @RequestParam(value = "password") String password);
}
