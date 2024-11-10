package com.hotel.system.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.system.controller.common.BaseController;
import com.hotel.system.dto.JsonResult;
import com.hotel.system.entity.Inrecord;
import com.hotel.system.entity.Order;
import com.hotel.system.service.InrecordService;
import com.hotel.system.service.OrderService;
import com.hotel.system.util.PageUtil;
import com.hotel.system.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * <pre>
 *    入住登记管理控制器
 * </pre>
 *
 * @author liuyanzhao
 * @date 2022/04/22
 */
@Controller
@RequestMapping(value = "/admin/inrecord")
public class InrecordController extends BaseController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(InrecordController.class);
    @Autowired
    private InrecordService inrecordService;

    @Autowired
    private OrderService orderService;


    /**
     * 查询所有留言记录并渲染Inrecord页面
     *
     * @return 模板路径admin/admin_inrecord
     */
    @GetMapping
    public String inrecordList(@RequestParam(value = "replyFlag", defaultValue = "-1") Integer replyFlag,
                               @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                               @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
                               @RequestParam(value = "sort", defaultValue = "createTime") String sort,
                               @RequestParam(value = "order", defaultValue = "desc") String order, Model model) {
        // 查询留言列表
        Page page = PageUtil.initMpPage(pageNumber, pageSize, sort, order);
        QueryWrapper<Inrecord> queryWrapper = new QueryWrapper<>();
        Page<Inrecord> inrecordPage = inrecordService.findAll(page, queryWrapper);
        for (Inrecord c : inrecordPage.getRecords()) {
            Order order1 = orderService.get(c.getOrderId());
            c.setOrder(order1 == null ? new Order() : order1);
        }
        model.addAttribute("inrecordList", inrecordPage.getRecords());
        model.addAttribute("pageInfo", PageUtil.convertPageVo(page));
        return "admin/admin_inrecord";
    }

    /**
     * 添加页面
     */
    @GetMapping("/new")
    public String add(@RequestParam(value = "orderId", required = false) Long orderId, Model model) {

        Order order = orderService.get(orderId);
        model.addAttribute("order", order == null ? new Order() : order);
        return "admin/admin_inrecord_add";
    }

    /**
     * 添加页面
     */
    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id") Long id, Model model) {

        Inrecord inrecord = inrecordService.get(id);
        Order order = orderService.get(inrecord.getOrderId());
        if (order != null) {
            inrecord.setOrderCode(order.getCode());
        }

        model.addAttribute("inrecord", inrecord);
        return "admin/admin_inrecord_edit";
    }


    /**
     * 保存
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public JsonResult saveInrecord(@ModelAttribute Inrecord inrecord) {
        if (StringUtils.isEmpty(inrecord.getName())
                || StringUtils.isEmpty(inrecord.getPhone())
                || StringUtils.isEmpty(inrecord.getIdCard())
                || inrecord.getPayDepositFlag() == null) {
            return JsonResult.success("请输入完整信息");
        }
        if (inrecord.getOrderId() == null) {
            Order order = orderService.findByCode(inrecord.getOrderCode());
            if (order == null) {
                return JsonResult.error("订单号不存在");
            }
            inrecord.setOrderId(order.getId());
            Order updateOrder = new Order();
            updateOrder.setId(order.getId());
            updateOrder.setCheckInStatus("已入住");
            orderService.update(updateOrder);
        }
        inrecordService.insertOrUpdate(inrecord);
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

        inrecordService.delete(cateId);
        return JsonResult.success("删除成功");
    }


}
