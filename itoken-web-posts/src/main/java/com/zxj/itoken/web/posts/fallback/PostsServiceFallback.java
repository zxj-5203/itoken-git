package com.zxj.itoken.web.posts.fallback;

import com.zxj.itoken.common.hystrix.Fallback;
import com.zxj.itoken.web.posts.service.PostsService;
import org.springframework.stereotype.Component;

/**
 * @author zxj
 * @date 2020/06/29
 */
@Component
public class PostsServiceFallback implements PostsService {
    @Override
    public String page(int pageNum, int pageSize, String tbPostsPostJson) {
        return Fallback.badGateway();
    }

    @Override
    public String get(String postGuid) {
        return null;
    }

    @Override
    public String save(String tbPostsPostJson, String optsBy) {
        return Fallback.badGateway();
    }
}
