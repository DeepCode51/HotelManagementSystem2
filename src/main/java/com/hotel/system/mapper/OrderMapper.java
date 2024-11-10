package com.hotel.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotel.system.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuyanzhao
 * @date: 2022/04/30
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据时间范围查询订单
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<Order> findAll(@Param("startDate") String startDate,
                        @Param("endDate") String endDate,
                        Page page);

    /**
     * 根据时间范围计算房费
     *
     * @param startDate
     * @param endDate
     * @return
     */
    Integer getTotalPriceSum(@Param("startDate") String startDate,
                             @Param("endDate") String endDate);

    List<String> getOrderCodeLike(@Param("code") String code);


    /**
     * 查询超时订单
     * @return
     */
    List<Order> findOverDueOrder();
}

