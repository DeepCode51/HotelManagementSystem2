package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;

/**
 * <pre>
 *     客房分类
 * </pre>
 *
 * @author : wpx
 * @date : 2022/04/30
 */
@TableName("category")
public class Category extends BaseEntity {

    /**
     * 分类名称
     */
    private String cateName;

    /**
     * 分类排序号
     */
    private Integer cateSort;

    /**
     * 分类描述
     */
    private String cateDesc;

    public Category() {
    }

    public Category(String cateName, Integer cateSort, String cateDesc) {
        this.cateName = cateName;
        this.cateSort = cateSort;
        this.cateDesc = cateDesc;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getCateSort() {
        return cateSort;
    }

    public void setCateSort(Integer cateSort) {
        this.cateSort = cateSort;
    }

    public String getCateDesc() {
        return cateDesc;
    }

    public void setCateDesc(String cateDesc) {
        this.cateDesc = cateDesc;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cateName='" + cateName + '\'' +
                ", cateSort=" + cateSort +
                ", cateDesc='" + cateDesc + '\'' +
                '}';
    }
}
