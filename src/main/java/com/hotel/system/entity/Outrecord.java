package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;


/**
 * 退房登记
 *
 * @author liuyanzhao
 * @date 2022/04/26 11:56 下午
 */
@TableName("outrecord")
public class Outrecord extends BaseEntity {

    /**
     * 订单id
     */
    private Long orderId;


    /**
     * 是否退还押金
     */
    private Integer returnDepositFlag;

    @TableField(exist = false)
    private Order order;

    @TableField(exist = false)
    private String orderCode;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getReturnDepositFlag() {
        return returnDepositFlag;
    }

    public void setReturnDepositFlag(Integer returnDepositFlag) {
        this.returnDepositFlag = returnDepositFlag;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
