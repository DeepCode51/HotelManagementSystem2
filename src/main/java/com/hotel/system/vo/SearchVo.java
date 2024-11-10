package com.hotel.system.vo;


import java.io.Serializable;

/**
 * @author liuyanzhao
 * @date 2022/04/26
 */
public class SearchVo implements Serializable {

    /**
     * 起始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    public SearchVo() {
    }

    public SearchVo(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "SearchVo{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
