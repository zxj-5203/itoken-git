package com.zxj.itoken.web.admin.service;

import com.zxj.itoken.common.web.service.BaseClientService;
import com.zxj.itoken.web.admin.fallback.AdminServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 根据 ID 获取管理员
     *
     * @return
     */
    @RequestMapping(value = "v1/admins", method = RequestMethod.GET)
    public String get(
            @RequestParam(required = true, value = "userCode") String userCode
    );

    /**
     * 保存管理员
     *
     * @return
     */
    @RequestMapping(value = "v1/admins", method = RequestMethod.POST)
    public String save(
            @RequestParam(required = true, value = "tbSysUserJson") String tbSysUserJson,
            @RequestParam(required = true, value = "optsBy") String optsBy
    );

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param tbSysUserJson
     * @return
     */
    @RequestMapping(value = "v1/admins/page/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public String page(
            @PathVariable(required = true, value = "pageNum") int pageNum,
            @PathVariable(required = true, value = "pageSize") int pageSize,
            @RequestParam(required = false, value = "tbSysUserJson") String tbSysUserJson
    );
}