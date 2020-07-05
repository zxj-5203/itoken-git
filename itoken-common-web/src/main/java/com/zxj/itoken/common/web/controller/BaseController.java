package com.zxj.itoken.common.web.controller;

import com.zxj.itoken.common.domain.BaseDomain;
import com.zxj.itoken.common.utils.MapperUtils;
import com.zxj.itoken.common.web.components.datatables.DataTablesResult;
import com.zxj.itoken.common.web.service.BaseClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * 通用 Controller
 * @author zxj
 * @date 2020/06/29
 */
// 所有被继承的 都要是 abstract 的
public abstract class BaseController<T extends BaseDomain, S extends BaseClientService> {

    /**
     * 注入业务逻辑层 的父类service
     */
    @Autowired
    protected S service;

    /**
     * 通用的分页方法
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public DataTablesResult page(HttpServletRequest request) {
        String strDraw = request.getParameter("draw");
        String strStart = request.getParameter("start");
        String strLength = request.getParameter("length");

        int draw = strDraw == null ? 0 : Integer.parseInt(strDraw);
        int start = strStart == null ? 0 : Integer.parseInt(strStart);
        int length = strLength == null ? 10 : Integer.parseInt(strLength);

        String json = service.page(start, length, null);
        DataTablesResult dataTablesResult = null;
        try {
            // BserResult 的数据，赋值给DataTableResult
            dataTablesResult = MapperUtils.json2pojo(json, DataTablesResult.class);
            dataTablesResult.setDraw(draw);
            dataTablesResult.setRecordsTotal(dataTablesResult.getCursor().getTotal());
            dataTablesResult.setRecordsFiltered(dataTablesResult.getCursor().getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataTablesResult;
    }
}
