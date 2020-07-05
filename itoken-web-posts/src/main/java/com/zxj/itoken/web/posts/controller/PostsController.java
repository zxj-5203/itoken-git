package com.zxj.itoken.web.posts.controller;

import com.zxj.itoken.common.domain.TbPostsPost;
import com.zxj.itoken.common.domain.TbSysUser;
import com.zxj.itoken.common.dto.BaseResult;
import com.zxj.itoken.common.utils.MapperUtils;
import com.zxj.itoken.common.web.constants.WebConstants;
import com.zxj.itoken.common.web.controller.BaseController;
import com.zxj.itoken.web.posts.service.PostsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 继承 BaseController 即继承通用的分页方法
 *
 * @author zxj
 * @date 2020/06/28
 */
@Controller
public class PostsController extends BaseController<TbPostsPost, PostsService> {
    @Autowired
    private PostsService postsService;

    /**
     * 根据 postGuid 查询文章内容；
     *
     * @param postGuid
     * @return
     * @ModelAttribute 注解 将方法的返回值放到request中，传回页面；
     * 所有的添加 @ModelAttribute 注解的方法，都会会 @RequestMapping 之前执行；
     */
    @ModelAttribute
    public TbPostsPost tbPostsPost(String postGuid) {
        TbPostsPost tbPostsPost = null;
        if (StringUtils.isBlank(postGuid)) {
            tbPostsPost = new TbPostsPost();
        } else {
            // get方法熔断的话，返回null
            String json = postsService.get(postGuid);
            try {
                // json 为空的话，就会抛异常，tbPostsPost就直接返回Null
                tbPostsPost = MapperUtils.json2pojo(json, TbPostsPost.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 二次确认 是否为空；为空的话，返回到前端会报错；
            if (tbPostsPost == null) {
                tbPostsPost = new TbPostsPost();
            }
        }
        return tbPostsPost;
    }

    /**
     * 跳转到首页
     *
     * @return
     */
    @GetMapping(value = {"", "main"})
    public String main() {
        return "main";
    }

    /**
     * 跳转列表页
     *
     * @return
     */
    @GetMapping(value = {"index"})
    public String index() {
        return "index";
    }


    /**
     * 跳转表单页面
     *
     * @return
     */
    @GetMapping(value = "form")
    public String form() {
        return "form";
    }

    /**
     * 保存文章
     *
     * @param request
     * @param redirectAttributes
     * @param tbPostsPost
     * @return
     * @throws Exception
     */
    @PostMapping(value = "save")
    public String save(HttpServletRequest request, RedirectAttributes redirectAttributes, TbPostsPost tbPostsPost) throws Exception {
        // 初始化
        tbPostsPost.setTimePublished(new Date());
        tbPostsPost.setStatus("0");

        // 从 session 中获取登录的用户的信息
        TbSysUser admin = (TbSysUser) request.getSession().getAttribute(WebConstants.SESSION_USER);

        String tbPostsPostJson = MapperUtils.obj2json(tbPostsPost);
        String json = postsService.save(tbPostsPostJson, admin.getUserCode());
        BaseResult baseResult = MapperUtils.json2pojo(json, BaseResult.class);

        // 将保存结果 存到redirectAttributes
        redirectAttributes.addFlashAttribute("baseResult", baseResult);

        if (baseResult.getSuccess().endsWith("成功")) {
            return "redirect:/index";
        }

        return "redirect:/form";
    }
}
