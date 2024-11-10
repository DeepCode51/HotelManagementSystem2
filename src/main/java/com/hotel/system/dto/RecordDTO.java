package com.hotel.system.dto;

/**
 * 预定记录DTO
 * @author xuxin
 * @date 2022/09/23 3:25 下午
 */
public class RecordDTO {

    /**
     * 房型id
     */
    private Long cateId;

    /**
     * 房型名称
     */
    private String cateName;

    /**
     * 房型数量
     */
    private Integer categoryCount;

    public RecordDTO() {
    }

    public RecordDTO(Long cateId, String cateName, Integer categoryCount) {
        this.cateId = cateId;
        this.cateName = cateName;
        this.categoryCount = categoryCount;
    }

    public Integer getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(Integer categoryCount) {
        this.categoryCount = categoryCount;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    @Override
    public String toString() {
        return "RecordDTO{" +
                "cateId=" + cateId +
                ", cateName='" + cateName + '\'' +
                ", categoryCount=" + categoryCount +
                '}';
    }

}
