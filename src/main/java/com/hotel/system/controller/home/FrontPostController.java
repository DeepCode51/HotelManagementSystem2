package com.hotel.system.controller.home;

import com.hotel.system.controller.common.BaseController;
import com.hotel.system.dto.JsonResult;
import com.hotel.system.entity.*;
import com.hotel.system.enums.OrderStatusEnum;
import com.hotel.system.service.*;
import com.hotel.system.service.*;
import com.hotel.system.util.DateUtil;
import com.hotel.system.util.StringUtils;
import com.hotel.system.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author liuyanzhao
 * @date 2022/3/11 4:59 下午
 */
@Controller
public class FrontPostController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;

    @Autowired
    private InrecordService inrecordService;

    @Autowired
    private OutrecordService outrecordService;

    @Value("${openAlipay}")
    private Boolean openAlipay;

    /**
     * 帖子详情
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/post/{id}")
    public String postDetails(@PathVariable("id") Long id,
                              @RequestParam(value = "startDate", required = false) String start,
                              @RequestParam(value = "endDate", required = false) String end,
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

        // 客房
        Post post = postService.get(id);
        if (post == null) {
            return renderNotFound();
        }
        // 分类
        Category category = categoryService.get(post.getCateId());
        post.setCategory(category);
        model.addAttribute("post", post);

        String[] imgUrlList = post.getImgUrl().split(",");
        model.addAttribute("imgUrlList", imgUrlList);


        // 该房间的预定记录
        List<Record> recordList = recordService.findByPostId(id);
        model.addAttribute("recordList", recordList);

        // 分类列表
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("startDate", start);
        model.addAttribute("endDate", end);
        return "home/post";
    }


    /**
     * 结算页面
     *
     * @param postId
     * @param start
     * @param model
     * @return
     */
    @GetMapping("/checkout")
    public String checkout(@RequestParam("postId") Long postId,
                           @RequestParam(value = "startDate", required = false) String start,
                           @RequestParam(value = "endDate", required = false) String end,
                           Model model) {
        DateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT);

        Date today = new Date();
        Date tomorrow = DateUtil.dateAddOne(today);

        // 判断入住日期是否合法
        Date startDate = null;
        if (StringUtils.isEmpty(start)) {
            start = dateFormat.format(today);
        } else {
            try {
                startDate = dateFormat.parse(start);
                if (startDate.before(today)) {
                    start = dateFormat.format(today);
                }
            } catch (ParseException e) {
                start = dateFormat.format(today);
                e.printStackTrace();
            }
        }

        // 判断离店日期是否合法
        Date endDate = null;
        if (StringUtils.isEmpty(end)) {
            end = dateFormat.format(tomorrow);
        } else {
            try {
                endDate = dateFormat.parse(end);
                if (endDate.before(tomorrow)) {
                    end = dateFormat.format(tomorrow);
                }
            } catch (ParseException e) {
                end = dateFormat.format(tomorrow);
                e.printStackTrace();
            }
        }

        Post post = postService.get(postId);
        if (post == null) {
            return this.renderNotFound();
        }

        User user = getLoginUser();
        if (user == null) {
            return "redirect:/";
        }

        // 分类列表
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        Integer quantity = DateUtil.differentDays(startDate, endDate);
        model.addAttribute("post", post);
        model.addAttribute("startDate", start);
        model.addAttribute("endDate", end);
        model.addAttribute("quantity", quantity);
        model.addAttribute("user", user);
        return "home/checkout";
    }

    /**
     * 创建订单
     *
     * @param postId
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/checkOrder")
    @ResponseBody
    public JsonResult checkOrder(@RequestParam(value = "postId") Long postId,
                                 @RequestParam(value = "startDate") String start,
                                 @RequestParam(value = "endDate") String end) throws ParseException {
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("请先登录");
        }

        Post post = postService.get(postId);
        if (post == null) {
            return JsonResult.error("客房不存在");
        }

        DateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT);

        Date today = new Date();

        // 判断入住日期是否合法
        Date startDate = dateFormat.parse(start);
        if (startDate.before(today) && !Objects.equals(start, dateFormat.format(today))) {
            return JsonResult.error("不能预定过去的日期");
        }

        // 判断离店日期是否合法
        Date endDate = dateFormat.parse(end);
        int quantity = DateUtil.differentDays(startDate, endDate);
        if (quantity < 1) {
            return JsonResult.error("至少预定一天");
        }

        // 查询日期列表
        List<String> dateList = DateUtil.getBetweenDates(start, end);
        // 判断客房是否可以预定
        List<Record> recordList = recordService.findByPostIdAndRecordDate(postId, dateList);

        if (recordList.size() > 0) {
            Record record = recordList.get(0);
            return JsonResult.error("该房间" + record.getRecordDate() + "已被人预定，请重新选择房间和日期");
        }
        return JsonResult.success("可以预定");
    }

    /**
     * 创建订单
     *
     * @param postId
     * @param start
     * @param end
     * @param userName
     * @param userDisplayName
     * @param idCard
     * @return
     */
    @PostMapping("/order")
    @Transactional
    @ResponseBody
    public JsonResult addOrder(@RequestParam(value = "postId") Long postId,
                               @RequestParam(value = "startDate") String start,
                               @RequestParam(value = "endDate") String end,
                               @RequestParam(value = "userName") String userName,
                               @RequestParam(value = "userDisplayName") String userDisplayName,
                               @RequestParam(value = "idCard") String idCard) throws ParseException {
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("请先登录");
        }

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(userDisplayName)) {
            return JsonResult.error("请输入完整信息");
        }
        Post post = postService.get(postId);
        if (post == null) {
            return JsonResult.error("客房不存在");
        }

        DateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT);
        Date today = new Date();

        // 判断入住日期是否合法
        Date startDate = dateFormat.parse(start);
        if (startDate.before(today) && !Objects.equals(start, dateFormat.format(today))) {
            return JsonResult.error("不能预定过去的日期");
        }

        // 判断离店日期是否合法
        Date endDate = dateFormat.parse(end);
        int quantity = DateUtil.differentDays(startDate, endDate);
        if (quantity < 1) {
            return JsonResult.error("至少预定一天");
        }


        // 查询日期列表
        List<String> dateList = DateUtil.getBetweenDates(start, quantity);
        // 判断客房是否可以预定
        List<Record> recordList = recordService.findByPostIdAndRecordDate(postId, dateList);
        if (recordList.size() > 0) {
            Record record = recordList.get(0);
            return JsonResult.error("该房间" + record.getRecordDate() + "已被人预定，请重新选择房间和日期");
        }

        // 支付省略
        // 添加订单
        Order order = new Order();
        order.setCode(orderService.generateOrderCode());
        order.setPostId(postId);
        order.setQuantity(quantity);
        order.setStartDate(start);
        order.setEndDate(end);
        order.setName(userDisplayName);
        order.setPhone(userName);
        order.setIdCard(idCard);
        order.setUserId(user.getId());


        if (openAlipay) {
            // 如果开启支付宝支付，则设置订单状态为待支付
            order.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        } else {
            // 如果开启未支付宝支付，则设置订单状态为已支付
            order.setStatus(OrderStatusEnum.HAS_PAY.getCode());

        }
        order.setPostTitle(post.getPostTitle());
        order.setPostNumber(post.getNumber());
        order.setPrice(post.getPrice());
        order.setTotalPrice(post.getPrice() * quantity);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        orderService.insert(order);

        // 添加预定记录
        for (String recordDate : dateList) {
            Record record = new Record();
            record.setPostId(postId);
            record.setUserId(user.getId());
            record.setRecordDate(recordDate);
            recordService.insert(record);
        }

        return JsonResult.success("订单创建成功", order.getId());
    }

    @GetMapping("/order/{id}")
    public String order(@PathVariable("id") Long id, Model model) {
        Order order = orderService.get(id);
        if (order == null) {
            return this.renderNotFound();
        }

        User user = getLoginUser();
        if (user == null) {
            return "redirect:/login";
        }

        Boolean isCustomer = loginUserIsCustomer();
        if (!Objects.equals(order.getUserId(), user.getId()) && isCustomer) {
            return this.renderNotAllowAccess();
        }
        model.addAttribute("order", order);


        // 分类列表
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("user", userService.get(order.getUserId()));
        model.addAttribute("inrecord", inrecordService.findByOrderId(id));
        model.addAttribute("outrecord", outrecordService.findByOrderId(id));
        return "home/order";
    }


}
