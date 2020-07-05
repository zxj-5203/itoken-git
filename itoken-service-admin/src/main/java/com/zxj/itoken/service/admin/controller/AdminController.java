package com.zxj.itoken.service.admin.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.zxj.itoken.common.domain.TbSysUser;
import com.zxj.itoken.common.dto.BaseResult;
import com.zxj.itoken.common.utils.MapperUtils;
import com.zxj.itoken.service.admin.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author zxj
 * @date 2020/06/20
 */
@RestController
@RequestMapping(value = "v1/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;


    /**
     * 根据 ID 获取管理员
     *
     * @param userCode
     * @return
     */
    @GetMapping(value = "{userCode}")
    public BaseResult get(@PathVariable(value = "userCode", required = true) String userCode) {
        TbSysUser tbSysUser = new TbSysUser();
        tbSysUser.setUserCode(userCode);
        TbSysUser result = adminService.selectOne(tbSysUser);
        return BaseResult.ok(result);
    }

    /**
     * 保存管理员
     *
     * @param tbSysUserJson
     * @param optsBy
     * @return
     */
    @PostMapping()
    public BaseResult save(@RequestParam(required = true) String tbSysUserJson,
                           @RequestParam(required = true) String optsBy) {
        int result = 0;
        TbSysUser tbSysUser = null;
        try {
            tbSysUser = MapperUtils.json2pojo(tbSysUserJson, TbSysUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tbSysUser != null) {
            // 密码加密
            String password = DigestUtils.md5DigestAsHex(tbSysUser.getPassword().getBytes());
            tbSysUser.setPassword(password);

            // userCode 为空，表示新增用户
            if (StringUtils.isBlank(tbSysUser.getUserCode())) {
                tbSysUser.setUserCode(UUID.randomUUID().toString());
                result = adminService.insert(tbSysUser, optsBy);
            }

            // 修改用户
            else {
                result = adminService.update(tbSysUser, optsBy);
            }

            // 最少有一行数据受影响
            if (result > 0) {
                return BaseResult.ok("保存管理员成功");
            }

        }
        return BaseResult.ok("保存管理员失败");
    }


    /**
     * 测试save
     * @return
     */
    @PostMapping("test")
    public void testSave() {
        TbSysUser tbSysUser = new TbSysUser();
        tbSysUser.setUserCode("104b4534-6355-41af-9361-2de6fe385d2c");
        tbSysUser.setLoginCode("zxj2@163.com");
        tbSysUser.setUserName("zxj2");
        tbSysUser.setPassword("123456");
        tbSysUser.setUserType("0");
        tbSysUser.setMgrType("1");
        tbSysUser.setStatus("0");
        tbSysUser.setCreateBy(tbSysUser.getUserName());
        tbSysUser.setCreateDate(new Date());
        tbSysUser.setUpdateBy(tbSysUser.getUserName());
        tbSysUser.setUpdateDate(new Date());
        tbSysUser.setCorpCode("0");
        tbSysUser.setCorpName("itoken");
        String json = null;
        try {
            json = MapperUtils.obj2json(tbSysUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        save(json, tbSysUser.getUserName());
    }

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param tbSysUserJson
     * @return
     */
    @GetMapping(value = "page/{pageNum}/{pageSize}")
    public BaseResult page(@PathVariable(required = true) int pageNum,
                           @PathVariable(required = true) int pageSize,
                           @RequestParam(required = false) String tbSysUserJson) throws Exception {
        TbSysUser tbSysUser = null;
        if (tbSysUserJson != null) {
            tbSysUser = MapperUtils.json2pojo(tbSysUserJson, TbSysUser.class);
        }
        PageInfo<TbSysUser> pageInfo = adminService.page(pageNum, pageSize, tbSysUser);
        // 分页后的结果集
        List<TbSysUser> list = pageInfo.getList();
        // 封装Cursor
        BaseResult.Cursor cursor = new BaseResult.Cursor();
        cursor.setTotal(new Long(pageInfo.getTotal()).intValue());
        cursor.setOffset(pageInfo.getPageNum());
        cursor.setLimit(pageInfo.getPageSize());

        return BaseResult.ok(list, cursor);
    }


    /**
     * 登录TbSysUserMapper
     *
     * @param loginCode
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public BaseResult login(String loginCode, String password) {
        // 检查登录信息
        BaseResult baseResult = checkLogin(loginCode, password);
        if (baseResult != null) {
            return baseResult;
        }
        // 登录业务
        TbSysUser tbSysUser = adminService.login(loginCode, password);
        // 登录成功
        if (tbSysUser != null) {
            return BaseResult.ok(tbSysUser);
        }
        // 登录失败
        else {
            return BaseResult.notOk(Lists.newArrayList(new BaseResult.Error("", "登录失败")));
        }
    }

    /**
     * 检查登录
     *
     * @param loginCode
     * @param password
     * @return
     */
    public BaseResult checkLogin(String loginCode, String password) {
        BaseResult baseResult = null;

        if (StringUtils.isBlank(loginCode) || StringUtils.isBlank(password)) {
            baseResult = BaseResult.notOk(Lists.newArrayList(
                    new BaseResult.Error("loginCode", "登录账户不能为空"),
                    new BaseResult.Error("password", "密码不能为空")
            ));
        }

        return baseResult;
    }
}
