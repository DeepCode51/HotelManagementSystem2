package com.hotel.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.system.common.base.BaseService;
import com.hotel.system.entity.Order;


/**
 * 订单
 *
 * @author liuyanzhao
 * @date 2022/04/6 2:00 下午
 */
public interface OrderService extends BaseService<Order, Long> {

    /**
     * 根据时间范围查询总金额
     *
     * @param startDate
     * @param endDate
     * @return
     */
    Integer getTotalPriceSum(String startDate, String endDate);

    /**
     * 根据时间范围查询
     *
     * @param startDate
     * @param endDate
     * @param page
     * @return
     */
    Page<Order> findAll(String startDate, String endDate, Page<Order> page);

    /**
     * 生成订单编码
     *
     * @return
     */
    String generateOrderCode();

    Order findByCode(String code);
}
