package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;


/**
 * 入住登记
 *
 * @author liuyanzhao
 * @date 2022/04/26 11:56 下午
 */
@TableName("inrecord")
public class Inrecord extends BaseEntity {

    /**
     * 订单id
     */
    private Long orderId;


    /**
     * 手机号
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 是否支付押金
     */
    private Integer payDepositFlag;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getPayDepositFlag() {
        return payDepositFlag;
    }

    public void setPayDepositFlag(Integer payDepositFlag) {
        this.payDepositFlag = payDepositFlag;
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
