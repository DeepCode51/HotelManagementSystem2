package com.hotel.system.controller.home;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.system.controller.common.BaseController;
import com.hotel.system.dto.PostQueryCondition;
import com.hotel.system.entity.Category;
import com.hotel.system.entity.Post;
import com.hotel.system.service.CategoryService;
import com.hotel.system.service.PostService;
import com.hotel.system.util.DateUtil;
import com.hotel.system.util.PageUtil;
import com.hotel.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author liuyanzhao
 * @date 2022/3/9 11:00 上午
 */

@Controller
public class IndexController extends BaseController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;


    @RequestMapping("/")
    public String index(@RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
                        @RequestParam(value = "size", defaultValue = "9") Integer pageSize,
                        @RequestParam(value = "sort", defaultValue = "createTime") String sort,
                        @RequestParam(value = "order", defaultValue = "desc") String order,
                        @RequestParam(value = "startDate", required = false) String start,
                        @RequestParam(value = "endDate", required = false) String end,
                        @RequestParam(value = "cateId", defaultValue = "0") Long cateId,
                        Model model) {
        DateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT);

        Date today = new Date();
        Date tomorrow = DateUtil.dateAddOne(today);


        // 判断入住日期是否合法
        if (StringUtils.isEmpty(start)) {
            start = dateFormat.format(today);
        } else {
            try {
                Date startDate = dateFormat.parse(start);
                if (startDate.before(today)) {
                    start = dateFormat.format(today);
                }
            } catch (ParseException e) {
                start = dateFormat.format(today);
                e.printStackTrace();
            }
        }

        // 判断离店日期是否合法
        if (StringUtils.isEmpty(end)) {
            end = dateFormat.format(tomorrow);
        } else {
            try {
                Date endDate = dateFormat.parse(end);
                if (endDate.before(tomorrow)) {
                    end = dateFormat.format(tomorrow);
                }
            } catch (ParseException e) {
                end = dateFormat.format(tomorrow);
                e.printStackTrace();
            }
        }

        PostQueryCondition condition = new PostQueryCondition();
        // 查询日期列表
        List<String> dateList = DateUtil.getBetweenDates(start, end);
            condition.setDateList(dateList);
        condition.setCateId(cateId);
        Page page = PageUtil.initMpPage(pageNumber, pageSize, sort, order);
        Page<Post> postPage = postService.findPostByCondition(condition, page);
        model.addAttribute("posts", postPage);

        // 分类列表
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("startDate", start);
        model.addAttribute("endDate", end);
        model.addAttribute("cateId", cateId);
        return "home/index";
    }


}
