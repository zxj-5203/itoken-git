package com.zxj.itoken.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.zxj.itoken.common.domain.BaseDomain;
import com.zxj.itoken.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.MyMapper;

import java.util.Date;

/**
 * @author zxj
 * @date 2020/06/25
 */
@Service
@Transactional(readOnly = true)
public abstract class BaseServiceImpl<T extends BaseDomain, D extends MyMapper<T>> implements BaseService<T> {

    // 要注入mapper，但是不知道具体哪个mapper，所以需要在类上使用泛型限定；
    // 所有mapper都继承MyMapper，所以使用MyMapper进行限定
    @Autowired
    D dao;


    @Override
    @Transactional(readOnly = false)  // 增删改不能只读
    public int insert(T t, String createBy) {
        t.setCreateBy(createBy);
        t.setCreateDate(new Date());
        t.setUpdateBy(createBy);
        t.setUpdateDate(new Date());
        return dao.insert(t);
    }

    @Override
    @Transactional(readOnly = false)  // 增删改不能只读
    public int delete(T t) {
        return dao.delete(t);
    }

    @Override
    @Transactional(readOnly = false)  // 增删改不能只读
    public int update(T t, String updateBy) {
        t.setUpdateBy(updateBy);
        t.setUpdateDate(new Date());
        return dao.updateByPrimaryKey(t);
    }

    @Override
    public int count(T t) {
        return dao.selectCount(t);
    }

    @Override
    public T selectOne(T t) {
        return dao.selectOne(t);
    }

    @Override
    public PageInfo<T> page(int pageNum, int pageSize, T t) {
//        PageHelper pageHelper = new PageHelper();
//        pageHelper.startPage(pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);

        PageInfo<T> pageInfo = new PageInfo<>(dao.select(t));
        return pageInfo;
    }
}
