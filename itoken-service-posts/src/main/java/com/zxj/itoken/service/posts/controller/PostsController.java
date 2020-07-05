package com.zxj.itoken.service.posts.controller;

import com.github.pagehelper.PageInfo;
import com.zxj.itoken.common.domain.TbPostsPost;
import com.zxj.itoken.common.domain.TbSysUser;
import com.zxj.itoken.common.dto.BaseResult;
import com.zxj.itoken.common.utils.MapperUtils;
import com.zxj.itoken.service.posts.service.PostsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author zxj
 * @date 2020/06/26
 */
@RestController
@RequestMapping(value = "v1/posts")
public class PostsController {
    @Autowired
    private PostsService postsService;

    @GetMapping(value = "test")
    public void test() {
        System.out.println("123");
    }

    /**
     * 根据主键id，获取文章
     *
     * @param postGuid
     * @return
     */
    @GetMapping(value = "/{postGuid}")
    public BaseResult get(@PathVariable(value = "postGuid", required = true) String postGuid) {
        TbPostsPost tbPostsPost = new TbPostsPost();
        tbPostsPost.setPostGuid(postGuid);
        TbPostsPost result = postsService.selectOne(tbPostsPost);
        return BaseResult.ok(result);
    }

    /**
     * 保存文章
     *
     * @param tbPostsPostJson
     * @param optsBy
     * @return
     */
    @PostMapping
    public BaseResult save(@RequestParam(value = "tbPostsPostJson", required = true) String tbPostsPostJson,
                           @RequestParam(value = "optsBy", required = true) String optsBy) {

        int result = 0;

        TbPostsPost tbPostsPost = null;
        try {
            tbPostsPost = MapperUtils.json2pojo(tbPostsPostJson, TbPostsPost.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tbPostsPost != null) {
            // 主键为空，执行插入操作
            if (StringUtils.isBlank(tbPostsPost.getPostGuid())) {
                tbPostsPost.setPostGuid(UUID.randomUUID().toString());
                result = postsService.insert(tbPostsPost, optsBy);
            }
            // 更新
            else {
                result = postsService.update(tbPostsPost, optsBy);
            }
            if (result > 0) {
                return BaseResult.ok("保存文章成功");
            }
        }
        return BaseResult.ok("保存文章失败");
    }

    /**
     * 测试save
     */
    @GetMapping("testSave")
    public void testSave() {
        TbPostsPost tbPostsPost = new TbPostsPost();
        tbPostsPost.setPostGuid("d2ce915f-a067-4002-b919-a3eaa37d33f2");
        tbPostsPost.setTitle("title1");
        tbPostsPost.setTimePublished(new Date());
        tbPostsPost.setStatus("1");
        tbPostsPost.setCreateBy("104b4534-6355-41af-9361-2de6fe385d2c");
        tbPostsPost.setCreateDate(new Date());
        tbPostsPost.setUpdateBy("104b4534-6355-41af-9361-2de6fe385d2c");
        tbPostsPost.setUpdateDate(new Date());
        try {
            save(MapperUtils.obj2json(tbPostsPost), "104b4534-6355-41af-9361-2de6fe385d2c");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param tbPostsPostJson
     * @return
     */
    @ApiOperation(value = "文章服务分页查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "笔数", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "tbPostsPostJson", value = "对象 JSON 格式", required = false, dataTypeClass = String.class, paramType = "json")
    })
    @GetMapping(value = "page/{pageNum}/{pageSize}")
    public BaseResult page(@PathVariable(required = true) int pageNum,
                           @PathVariable(required = true) int pageSize,
                           @RequestParam(required = false) String tbPostsPostJson) throws Exception {
        TbPostsPost tbPostsPost = null;
        if (StringUtils.isNotBlank(tbPostsPostJson)) {
            tbPostsPost = MapperUtils.json2pojo(tbPostsPostJson, TbPostsPost.class);
        }

        PageInfo pageInfo = postsService.page(pageNum, pageSize, tbPostsPost);
        // 分页后的结果集
        List<TbPostsPost> lists = pageInfo.getList();

        // 封装Custor
        BaseResult.Cursor cursor = new BaseResult.Cursor();
        cursor.setTotal(new Long(pageInfo.getTotal()).intValue());
        cursor.setOffset(pageInfo.getPageNum());
        cursor.setLimit(pageInfo.getPageSize());

        return BaseResult.ok(lists, cursor);
    }

}
