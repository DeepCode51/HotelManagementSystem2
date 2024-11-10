package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;

import java.util.List;

/**
 * 留言
 *
 * @author liuyanzhao
 * @date 2022/04/26 11:56 下午
 */
@TableName("comment")
public class Comment extends BaseEntity {

    /**
     * 留言人
     */
    private Long userId;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 父id
     */
    private Long pid;

    /**
     * 是否回复，1回复，0未回复
     */
    private Integer replyFlag;

    /**
     * 留言人
     */
    @TableField(exist = false)
    private User user;

    /**
     * 回复列表
     */
    @TableField(exist = false)
    private List<Comment> replyList;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getReplyFlag() {
        return replyFlag;
    }

    public void setReplyFlag(Integer replyFlag) {
        this.replyFlag = replyFlag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Comment> replyList) {
        this.replyList = replyList;
    }
}
