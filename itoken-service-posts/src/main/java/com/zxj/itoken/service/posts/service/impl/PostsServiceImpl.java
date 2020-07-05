package com.zxj.itoken.service.posts.service.impl;

import com.zxj.itoken.common.domain.TbPostsPost;
import com.zxj.itoken.common.mapper.TbPostsPostMapper;
import com.zxj.itoken.common.service.impl.BaseServiceImpl;
import com.zxj.itoken.service.posts.service.PostsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zxj
 * @date 2020/06/26
 */
@Service
@Transactional(readOnly = true)
public class PostsServiceImpl extends BaseServiceImpl<TbPostsPost, TbPostsPostMapper> implements PostsService {
}
