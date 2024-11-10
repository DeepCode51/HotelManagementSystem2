package com.hotel.system.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.system.controller.common.BaseController;
import com.hotel.system.dto.JsonResult;
import com.hotel.system.entity.*;
import com.hotel.system.enums.OrderStatusEnum;
import com.hotel.system.service.*;
import com.hotel.system.util.DateUtil;
import com.hotel.system.util.PageUtil;
import com.hotel.system.util.StringUtils;
import com.hotel.system.entity.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     订单管理控制器
 * </pre>
 *
 * @author liuyanzhao
 * @date 2022/04/22
 */
@Controller
@RequestMapping(value = "/admin/order")
public class OrderController extends BaseController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private PostService postService;

    @Autowired
    private CleanService cleanService;

    @Autowired
    private InrecordService inrecordService;

    /**
     * 查询所有订单并渲染order页面
     *
     * @return 模板路径admin/admin_order
     */
    @GetMapping
    public String orders(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                         @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
                         @RequestParam(value = "sort", defaultValue = "id") String sort,
                         @RequestParam(value = "order", defaultValue = "desc") String order,
                         @RequestParam(value = "keywords", defaultValue = "") String keywords,
                         @RequestParam(value = "status", defaultValue = "-1") Integer status,
                         Model model) {
        Page page = PageUtil.initMpPage(pageNumber, pageSize, sort, order);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(keywords)) {
            queryWrapper.and(wrapper -> wrapper.like("code", keywords).or().like("name", keywords)
                    .or().like("id_card", keywords).like("phone", keywords));
        }
        if (status != -1) {
            queryWrapper.eq("status", status);
        }
        Boolean isCustomer = loginUserIsCustomer();
        if (isCustomer) {
            queryWrapper.eq("user_id", getLoginUserId());

        }
        Page<Order> orderPage = orderService.findAll(page, queryWrapper);

        List<Order> list = orderPage.getRecords();
        for(Order order1 : list){
            Inrecord inrecord = inrecordService.findByOrderId(order1.getId());

            order1.setInrecord(inrecord);
        }


        model.addAttribute("orders", list);
        model.addAttribute("pageInfo", PageUtil.convertPageVo(page));
        model.addAttribute("keywords", keywords);
        model.addAttribute("status", status);

        model.addAttribute("isCustomer", isCustomer);
        return "admin/admin_order";
    }


    /**
     * 删除订单
     *
     * @param id 订单Id
     * @return JsonResult
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public JsonResult delete(@RequestParam("id") Long id) {
        Order order = orderService.get(id);
        if (order == null) {
            return JsonResult.error("订单不存在");
        }

        orderService.delete(id);


        Long postId = order.getPostId();
        Long userId = order.getUserId();
        List<String> dateList = DateUtil.getBetweenDates(order.getStartDate(), order.getQuantity());

        // 释放预定
        recordService.delete(postId, userId, dateList);
        return JsonResult.success("删除成功");
    }


    /**
     * 完结订单
     *
     * @param id 订单Id
     * @return JsonResult
     */
    @PostMapping(value = "/finish")
    @ResponseBody
    public JsonResult finish(@RequestParam("id") Long id) {
        User user = getLoginUser();

        Order order = orderService.get(id);
        if (order == null) {
            return JsonResult.error("订单不存在");
        }

        order.setStatus(OrderStatusEnum.FINISHED.getCode());
        order.setCheckInStatus("已退房");
        orderService.update(order);

        Post post = postService.get(order.getPostId());
        if (post != null) {
            // 创建清洁通知
            Clean clean = new Clean();
            clean.setCreateTime(new Date());
            clean.setUpdateTime(new Date());
            clean.setCreateBy(user.getCreateBy());
            clean.setPostTitle(post.getPostTitle());
            clean.setNumber(post.getNumber());
            clean.setStatus(0);
            clean.setOrderId(id);
            clean.setPostId(post.getId());
            cleanService.insert(clean);
        }

        Long postId = order.getPostId();
        Long userId = order.getUserId();
        List<String> dateList = DateUtil.getBetweenDates(order.getStartDate(), order.getQuantity());

        // 释放预定
        recordService.delete(postId, userId, dateList);
        return JsonResult.success("退房成功");
    }

    /**
     * 取消订单
     *
     * @param id 订单Id
     * @return JsonResult
     */
    @PostMapping(value = "/cancel")
    @ResponseBody
    @Transactional
    public JsonResult close(@RequestParam("id") Long id) {
        // 修改订单状态
        Order order = orderService.get(id);
        if (order == null) {
            return JsonResult.error("订单不存在");
        }

        order.setStatus(OrderStatusEnum.CANCEL.getCode());
        orderService.update(order);

        Long postId = order.getPostId();
        Long userId = order.getUserId();
        List<String> dateList = DateUtil.getBetweenDates(order.getStartDate(), order.getQuantity());

        // 释放预定
        recordService.delete(postId, userId, dateList);
        return JsonResult.success("关闭成功");
    }

    /**
     * 财务页面
     *
     * @param model
     * @return
     */
    @GetMapping("/finance")
    public String finance(@RequestParam(value = "startDate", defaultValue = "") String startDate,
                          @RequestParam(value = "endDate", defaultValue = "") String endDate,
                          @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                          @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
                          @RequestParam(value = "sort", defaultValue = "id") String sort,
                          @RequestParam(value = "order", defaultValue = "desc") String order,
                          Model model) {

        Page page = PageUtil.initMpPage(pageNumber, pageSize, sort, order);
        Page<Order> orderPage = orderService.findAll(startDate, endDate, page);
        model.addAttribute("orders", orderPage.getRecords());
        model.addAttribute("pageInfo", PageUtil.convertPageVo(page));

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        Integer totalPrice = orderService.getTotalPriceSum(startDate, endDate);
        model.addAttribute("totalPrice", totalPrice == null ? 0 : totalPrice);
        return "admin/admin_finance";
    }

    /**
     * 续订
     *
     * @param id 订单Id
     * @return JsonResult
     */
    @PostMapping(value = "/extend")
    @ResponseBody
    public JsonResult extend(@RequestParam("id") Long id,
                             @RequestParam("endDate") String end) throws ParseException {
        User user = getLoginUser();

        Order order = orderService.get(id);
        if (order == null) {
            return JsonResult.error("订单不存在");
        }

        // 判断离店日期是否合法
        DateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT);
        Date endDate = dateFormat.parse(end);

        Date oldEndDate = dateFormat.parse(order.getEndDate());

        if (!endDate.after(oldEndDate)) {
            return JsonResult.error("新的退房日期不能小于等于之前的退房日期");
        }


        int quantity = DateUtil.differentDays(oldEndDate, endDate);
        if (quantity < 1) {
            return JsonResult.error("至少需要续订一天");
        }


        // 查询日期列表
        List<String> dateList = DateUtil.getBetweenDates(order.getEndDate(), quantity);
        // 判断客房是否可以预定
        List<Record> recordList = recordService.findByPostIdAndRecordDate(order.getPostId(), dateList);
        if (recordList.size() > 0) {
            Record record = recordList.get(0);
            return JsonResult.error("该房间" + record.getRecordDate() + "已被人预定，请重新选择续订日期");
        }

        order.setQuantity(order.getQuantity() + quantity);
        order.setEndDate(dateFormat.format(endDate));
        order.setTotalPrice(order.getPrice() * quantity + order.getTotalPrice());
        orderService.update(order);

        // 添加预定记录
        for (String recordDate : dateList) {
            Record record = new Record();
            record.setPostId(order.getPostId());
            record.setUserId(user.getId());
            record.setRecordDate(recordDate);
            recordService.insert(record);
        }
        return JsonResult.success("续订成功");
    }


}
