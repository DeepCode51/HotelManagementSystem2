package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;

import java.util.Date;

/**
 * 打扫房间记录
 *
 * @author liuyanzhao
 * @date 2022/04/26 11:56 下午
 */
@TableName("clean")
public class Clean extends BaseEntity {

    /**
     * 客房标题
     */
    private String postTitle;

    /**
     * 0 待打扫
     * 1 正在打扫
     * 2 已完成
     */
    private Integer status;

    /**
     * 房间编号
     */
    private String number;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 打扫时间
     */
    private Date finishTime;


    private Long orderId;
    private Long postId;

    public Clean() {
    }

    public Clean(String postTitle, Integer status, String number, Date startTime, Date finishTime) {
        this.postTitle = postTitle;
        this.status = status;
        this.number = number;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Clean{" +
                "postTitle='" + postTitle + '\'' +
                ", status=" + status +
                ", number='" + number + '\'' +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                '}';
    }
}
