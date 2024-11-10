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
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * <pre>
 *    退房登记管理控制器
 * </pre>
 *
 * @author liuyanzhao
 * @date 2022/04/22
 */
@Controller
@RequestMapping(value = "/admin/outrecord")
public class OutrecordController extends BaseController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OutrecordController.class);
    @Autowired
    private OutrecordService outrecordService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private PostService postService;
    @Autowired
    private CleanService cleanService;


    @Autowired
    private RecordService recordService;

    /**
     * 查询所有留言记录并渲染Outrecord页面
     *
     * @return 模板路径admin/admin_outrecord
     */
    @GetMapping
    public String outrecordList(@RequestParam(value = "replyFlag", defaultValue = "-1") Integer replyFlag,
                                @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "sort", defaultValue = "createTime") String sort,
                                @RequestParam(value = "order", defaultValue = "desc") String order, Model model) {
        // 查询留言列表
        Page page = PageUtil.initMpPage(pageNumber, pageSize, sort, order);
        QueryWrapper<Outrecord> queryWrapper = new QueryWrapper<>();
        Page<Outrecord> outrecordPage = outrecordService.findAll(page, queryWrapper);
        for (Outrecord c : outrecordPage.getRecords()) {
            Order order1 = orderService.get(c.getOrderId());
            c.setOrder(order1 == null ? new Order() : order1);
        }
        model.addAttribute("outrecordList", outrecordPage.getRecords());
        model.addAttribute("pageInfo", PageUtil.convertPageVo(page));
        return "admin/admin_outrecord";
    }

    /**
     * 添加页面
     */
    @GetMapping("/new")
    public String add(@RequestParam(value = "orderId", required = false) Long orderId, Model model) {

        Order order = orderService.get(orderId);
        model.addAttribute("order", order == null ? new Order() : order);
        return "admin/admin_outrecord_add";
    }

    /**
     * 添加页面
     */
    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id") Long id, Model model) {

        Outrecord outrecord = outrecordService.get(id);
        Order order = orderService.get(outrecord.getOrderId());
        if (order != null) {
            outrecord.setOrderCode(order.getCode());
        }

        model.addAttribute("outrecord", outrecord);
        return "admin/admin_outrecord_edit";
    }


    /**
     * 保存
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public JsonResult saveOutrecord(@ModelAttribute Outrecord outrecord) {
        if (outrecord.getReturnDepositFlag() == null) {
            return JsonResult.success("请输入完整信息");
        }
        User user = getLoginUser();
        if (outrecord.getOrderId() == null) {
            Order order = orderService.findByCode(outrecord.getOrderCode());
            if (order == null) {
                return JsonResult.error("订单号不存在");
            }
            outrecord.setOrderId(order.getId());
            Order updateOrder = new Order();
            updateOrder.setId(order.getId());
            updateOrder.setCheckInStatus("已退房");
            updateOrder.setStatus(OrderStatusEnum.FINISHED.getCode());
            orderService.update(updateOrder);


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
                clean.setOrderId(order.getId());
                clean.setPostId(post.getId());
                cleanService.insert(clean);
            }

            Long postId = order.getPostId();
            Long userId = order.getUserId();
            List<String> dateList = DateUtil.getBetweenDates(order.getStartDate(), order.getQuantity());

            // 释放预定
            recordService.delete(postId, userId, dateList);
        }
        outrecordService.insertOrUpdate(outrecord);

        return JsonResult.success("保存成功");
    }

    /**
     * 删除
     *
     * @param cateId id
     * @return JsonResult
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public JsonResult delete(@RequestParam("id") Long cateId) {

        outrecordService.delete(cateId);
        return JsonResult.success("删除成功");
    }


}
