package com.hotel.system.config.schedule;

import com.hotel.system.entity.Order;
import com.hotel.system.enums.OrderStatusEnum;
import com.hotel.system.mapper.OrderMapper;
import com.hotel.system.service.RecordService;
import com.hotel.system.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定时任务
 * 订单创建时间超过30分钟未支付的，取消订单
 *
 * @author 言曌
 * @date 2020/3/21 7:18 下午
 */
@Component
public class OrderSchedule {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RecordService recordService;

    /**
     * 更新到期的订单
     */
    @Scheduled(fixedRate = 10000)
    @Transactional(rollbackFor = Exception.class)
    public void updatePostStatus() {
        List<Order> orderList = orderMapper.findOverDueOrder();
        for (Order order : orderList) {
            // 更新订单状态为已取消
            order.setStatus(OrderStatusEnum.PAY_CANCEL.getCode());
            orderMapper.updateById(order);
            // 释放房间
            Long postId = order.getPostId();
            Long userId = order.getUserId();
            List<String> dateList = DateUtil.getBetweenDates(order.getStartDate(), order.getQuantity());

            // 释放预定
            recordService.delete(postId, userId, dateList);
        }


    }

}
