package com.hotel.system.controller.home;


import cn.hutool.http.HtmlUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.system.controller.common.BaseController;
import com.hotel.system.dto.JsonResult;
import com.hotel.system.entity.Category;
import com.hotel.system.entity.Comment;
import com.hotel.system.entity.User;
import com.hotel.system.service.CategoryService;
import com.hotel.system.service.CommentService;
import com.hotel.system.service.UserService;
import com.hotel.system.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Controller
public class FrontCommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * 留言列表页面
     *
     * @param pageNumber
     * @param pageSize
     * @param sort
     * @param order
     * @param model
     * @return
     */
    @GetMapping("/comment")
    public String index(@RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
                        @RequestParam(value = "size", defaultValue = "5") Integer pageSize,
                        @RequestParam(value = "sort", defaultValue = "createTime") String sort,
                        @RequestParam(value = "order", defaultValue = "desc") String order,
                        Model model) {

        // 顶部的菜单，分类列表
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        // 分页查询留言
        // 只查询pid为0的一级留言
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("pid", 0);
        Page page = PageUtil.initMpPage(pageNumber, pageSize, sort, order);
        Page<Comment> commentPage = commentService.findAll(page, queryWrapper);
        for(Comment c : commentPage.getRecords()) {
            // 查询留言的用户
            User user = userService.get(c.getUserId());
            c.setUser(user == null ? new User() : user);

            // 查询留言的回复列表
            QueryWrapper<Comment> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("pid", c.getId());
            List<Comment> replyList = commentService.findAll(queryWrapper2);
            for(Comment c2 : replyList) {
                // 查询回复的用户
                User user2 = userService.get(c2.getUserId());
                c2.setUser(user2 == null ? new User() : user2);
            }
            c.setReplyList(replyList);
        }


        model.addAttribute("comments", commentPage);
        return "home/comment";
    }

    /**
     * 留言
     *
     * @param content
     * @return
     */
    @PostMapping("/comment/submit")
    @ResponseBody
    public JsonResult submitComment(String content) {
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("用户未登录");
        }
        Comment comment = new Comment();
        comment.setCreateTime(new Date());
        comment.setUserId(user.getId());
        comment.setContent(HtmlUtil.escape(content));
        comment.setReplyFlag(0);
        commentService.insert(comment);
        return JsonResult.success("留言成功，耐心等待工作人员回复");
    }
}
