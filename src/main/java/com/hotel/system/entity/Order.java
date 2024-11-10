package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;

/**
 * 订单
 * @author liuyanzhao
 * @date 2022/04/05 3:25 下午
 */
@TableName("t_order")
public class Order extends BaseEntity {

    /**
     * 订单编码
     */
    private String code;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 房间ID
     */
    private Long postId;

    /**
     * 天数
     */
    private Integer quantity;

    /**
     * 住客姓名
     */
    private String name;

    /**
     * 联系手机
     */
    private String phone;


    /**
     * 身份证
     */
    private String idCard;

    /**
     * 入店日期
     */
    private String startDate;

    /**
     * 退房日期
     */
    private String endDate;


    /**
     * 订单状态：0待支付，1已支付，2已退房, 3已取消
     */
    private Integer status;

    private Integer price;
    private Integer totalPrice;
    private String postTitle;
    private String postNumber;

    private String checkInStatus;


    @TableField(exist = false)
    private Inrecord inrecord;

    public Inrecord getInrecord() {
        return inrecord;
    }

    public void setInrecord(Inrecord inrecord) {
        this.inrecord = inrecord;
    }

    public String getCheckInStatus() {
        return checkInStatus;
    }

    public void setCheckInStatus(String checkInStatus) {
        this.checkInStatus = checkInStatus;
    }

    public Order() {
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId=" + userId +
                ", postId=" + postId +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", idCard='" + idCard + '\'' +
                ", startDate='" + startDate + '\'' +
                ", status=" + status +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", postTitle='" + postTitle + '\'' +
                ", postNumber='" + postNumber + '\'' +
                '}';
    }
}
