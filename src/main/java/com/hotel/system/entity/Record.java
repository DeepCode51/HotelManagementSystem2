package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;

/**
 * 预定记录
 * @author liuyanzhao
 * @date 2022/04/05 3:25 下午
 */
@TableName("record")
public class Record extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 房间ID
     */
    private Long postId;

    /**
     * 入店日期
     */
    private String recordDate;

    public Record() {
    }

    public Record(Long userId, Long postId, String recordDate) {
        this.userId = userId;
        this.postId = postId;
        this.recordDate = recordDate;
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

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public String toString() {
        return "Record{" +
                "userId=" + userId +
                ", postId=" + postId +
                ", recordDate='" + recordDate + '\'' +
                '}';
    }
}
