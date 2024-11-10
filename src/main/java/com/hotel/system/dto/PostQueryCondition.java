package com.hotel.system.dto;


import java.util.List;

/**
 * @author liuyanzhao
 * @date 2022/3/12 4:53 下午
 */
public class PostQueryCondition {

    /**
     * 用户ID
     */
    private Long userId;


    /**
     * 分类ID
     */
    private Long cateId;

    /**
     * 预订的日期集合
     */
    private List<String> dateList;

    public PostQueryCondition() {
    }

    public PostQueryCondition(Long userId, Long cateId, List<String> dateList) {
        this.userId = userId;
        this.cateId = cateId;
        this.dateList = dateList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    @Override
    public String toString() {
        return "PostQueryCondition{" +
                "userId=" + userId +
                ", cateId=" + cateId +
                ", dateList=" + dateList +
                '}';
    }
}
