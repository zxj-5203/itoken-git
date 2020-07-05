package com.zxj.itoken.web.posts.service;

import com.zxj.itoken.common.web.service.BaseClientService;
import com.zxj.itoken.web.posts.fallback.PostsServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zxj
 * @date 2020/06/29
 */
@FeignClient(value = "itoken-service-posts", fallback = PostsServiceFallback.class)
public interface PostsService extends BaseClientService {
    @GetMapping(value = "v1/posts/page/{pageNum}/{pageSize}")
    public String page(
            @PathVariable(required = true, value = "pageNum") int pageNum,
            @PathVariable(required = true, value = "pageSize") int pageSize,
            @RequestParam(required = false, value = "tbPostsPostJson") String tbPostsPostJson
    );

    @GetMapping(value = "v1/posts/{postGuid}")
    public String get(
            @PathVariable(required = true, value = "postGuid") String postGuid
    );

    @PostMapping(value = "v1/posts")
    public String save(
            @RequestParam(required = true, value = "tbPostsPostJson") String tbPostsPostJson,
            @RequestParam(required = true, value = "optsBy") String optsBy
    );
}
