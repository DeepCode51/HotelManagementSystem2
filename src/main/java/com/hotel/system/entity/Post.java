package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;


/**
 * 客房信息
 * @author liuyanzhao
 * @date 2022/04/05
 */
@TableName("post")
public class Post extends BaseEntity {

    /**
     * 客房标题
     */
    private String postTitle;

    /**
     * 客房描述
     */
    private String postContent;

    /**
     * 客房摘要
     */
    private String postSummary;

    /**
     * 缩略图
     */
    private String postThumbnail;

    /**
     * 0 正常
     * 1 已预订
     * 2 下架
     */
    private Integer postStatus;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 房间编号
     */
    private String number;

    /**
     * 分类ID
     */
    private Long cateId;

    /**
     * 图片URL
     */
    private String imgUrl;

    /**
     * 富文本
     */
    private String postEditor;

    /**
     * 客房所属分类
     */
    @TableField(exist = false)
    private Category category;

    public Post() {
    }

    public Post(String postTitle, String postContent, String postSummary, String postThumbnail, Integer postStatus, Integer price, String number, Long cateId, String imgUrl, String postEditor, Category category) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postSummary = postSummary;
        this.postThumbnail = postThumbnail;
        this.postStatus = postStatus;
        this.price = price;
        this.number = number;
        this.cateId = cateId;
        this.imgUrl = imgUrl;
        this.postEditor = postEditor;
        this.category = category;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostSummary() {
        return postSummary;
    }

    public void setPostSummary(String postSummary) {
        this.postSummary = postSummary;
    }

    public String getPostThumbnail() {
        return postThumbnail;
    }

    public void setPostThumbnail(String postThumbnail) {
        this.postThumbnail = postThumbnail;
    }

    public Integer getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(Integer postStatus) {
        this.postStatus = postStatus;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPostEditor() {
        return postEditor;
    }

    public void setPostEditor(String postEditor) {
        this.postEditor = postEditor;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postTitle='" + postTitle + '\'' +
                ", postContent='" + postContent + '\'' +
                ", postSummary='" + postSummary + '\'' +
                ", postThumbnail='" + postThumbnail + '\'' +
                ", postStatus=" + postStatus +
                ", price=" + price +
                ", number='" + number + '\'' +
                ", cateId=" + cateId +
                ", imgUrl='" + imgUrl + '\'' +
                ", postEditor='" + postEditor + '\'' +
                ", category=" + category +
                '}';
    }
}