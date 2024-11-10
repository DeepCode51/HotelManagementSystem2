package com.hotel.system.service.impl;

import com.hotel.system.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.system.entity.Order;
import com.hotel.system.mapper.OrderMapper;
import com.hotel.system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author liuyanzhao
 * @date 2022/04/06 2:01 下午
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public BaseMapper<Order> getRepository() {
        return orderMapper;
    }

    @Override
    public QueryWrapper<Order> getQueryWrapper(Order order) {
        //对指定字段查询
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if (order != null) {
            if (order.getUserId() != null) {
                queryWrapper.eq("user_id", order.getUserId());
            }
            if (order.getPostId() != null) {
                queryWrapper.eq("post_id", order.getPostId());
            }
            if (StringUtils.isNotEmpty(order.getPhone())) {
                queryWrapper.eq("phone", order.getPhone());
            }
            if (order.getStatus() != null) {
                queryWrapper.eq("status", order.getStatus());
            }
            if (order.getStartDate() != null) {
                queryWrapper.eq("start_date", order.getStartDate());
            }
        }
        return queryWrapper;
    }

    @Override
    public Integer getTotalPriceSum(String startDate, String endDate) {
        return orderMapper.getTotalPriceSum(startDate, endDate);
    }

    @Override
    public Page<Order> findAll(String startDate, String endDate, Page<Order> page) {
        return page.setRecords(orderMapper.findAll(startDate, endDate, page));
    }

    @Override
    public synchronized String generateOrderCode() {
        StringBuilder orderCode = new StringBuilder("DD");
        SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
        String date = sdf.format(new Date());
        orderCode.append(date);
        List<String> orderCodeList = orderMapper.getOrderCodeLike(orderCode.toString());
        Integer maxNum = 1;
        for (String code : orderCodeList) {
            Integer num = Integer.valueOf(code.replace(orderCode.toString(), ""));
            maxNum = Math.max(maxNum, num);
        }
        maxNum = maxNum + 1;
        if(maxNum < 9999) {
            orderCode.append(StringUtils.autoGenericCode(String.valueOf(maxNum), 4));
        } else {
            orderCode.append(StringUtils.autoGenericCode(String.valueOf(maxNum), 10));
        }
        return orderCode.toString();
    }

    @Override
    public Order findByCode(String code) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return orderMapper.selectOne(queryWrapper);
    }


}
