package com.hotel.system.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.system.controller.common.BaseController;
import com.hotel.system.dto.JsonResult;
import com.hotel.system.entity.Comment;
import com.hotel.system.entity.User;
import com.hotel.system.service.*;
import com.hotel.system.service.CommentService;
import com.hotel.system.service.UserService;
import com.hotel.system.util.PageUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     留言管理控制器
 * </pre>
 *
 * @author liuyanzhao
 * @date 2022/04/22
 */
@Controller
@RequestMapping(value = "/admin/comment")
public class CommentController extends BaseController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;


    /**
     * 查询所有留言记录并渲染Comment页面
     *
     * @return 模板路径admin/admin_comment
     */
    @GetMapping
    public String commentList(@RequestParam(value = "replyFlag", defaultValue = "-1") Integer replyFlag,
                              @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                              @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
                              @RequestParam(value = "sort", defaultValue = "createTime") String sort,
                              @RequestParam(value = "order", defaultValue = "desc") String order, Model model) {
        // 查询留言列表
        Page page = PageUtil.initMpPage(pageNumber, pageSize, sort, order);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        if (replyFlag != -1) {
            queryWrapper.eq("reply_flag", replyFlag);
        }
        queryWrapper.eq("pid", 0);
        Page<Comment> commentPage = commentService.findAll(page, queryWrapper);
        for (Comment c : commentPage.getRecords()) {
            // 查询留言的用户
            User user = userService.get(c.getUserId());
            c.setUser(user == null ? new User() : user);

            if (c.getContent().length() > 200) {
                c.setContent(c.getContent().substring(0, 200) + "...");
            }
        }
        model.addAttribute("commentList", commentPage.getRecords());
        model.addAttribute("pageInfo", PageUtil.convertPageVo(page));
        model.addAttribute("replyFlag", replyFlag);

        // 只有管理员能删除
        model.addAttribute("isAdmin", loginUserIsAdmin());
        return "admin/admin_comment";
    }

    @GetMapping("/info")
    public String info(Long id, Model model) {
        Comment comment = commentService.get(id);
        if (comment == null) {
            return this.renderNotFound();
        }
        // 查询留言的用户
        User user = userService.get(comment.getUserId());
        comment.setUser(user == null ? new User() : user);

        // 查询留言的回复列表
        QueryWrapper<Comment> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("pid", comment.getId());
        List<Comment> replyList = commentService.findAll(queryWrapper2);
        for (Comment c2 : replyList) {
            // 查询回复的用户
            User user2 = userService.get(c2.getUserId());
            c2.setUser(user2 == null ? new User() : user2);
        }
        comment.setReplyList(replyList);
        model.addAttribute("comment", comment);
        return "admin/admin_comment_info";
    }

    /**
     * 回复留言
     */
    @PostMapping(value = "/reply")
    @ResponseBody
    public JsonResult replyComment(@RequestParam String content,
                                   @RequestParam Long pid) {
        Comment comment = commentService.get(pid);
        if (comment == null) {
            return JsonResult.error("留言不存在");
        }
        // 更新留言是否被回复状态为已回复
        comment.setReplyFlag(1);
        commentService.update(comment);

        // 添加回复
        Comment reply = new Comment();
        reply.setUserId(getLoginUserId());
        reply.setCreateTime(new Date());
        reply.setPid(pid);
        reply.setContent(content);
        reply.setReplyFlag(0);
        commentService.insert(reply);
        return JsonResult.success("回复成功");
    }

    /**
     * 删除留言记录
     *
     * @param cateId 留言记录Id
     * @return JsonResult
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public JsonResult delete(@RequestParam("id") Long cateId) {

        commentService.delete(cateId);
        return JsonResult.success("删除成功");
    }


}
