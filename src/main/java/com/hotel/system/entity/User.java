package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * 用户信息
 * @author liuyanzhao
 * @date 2022/04/05
 */
@TableName("user")
public class User extends BaseEntity {

    /**
     * 手机号
     */
    private String userName;

    /**
     * 用户名
     */
    private String userDisplayName;

    /**
     * 密码
     */
    @JsonIgnore
    private String userPass;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 头像
     */
    private String userAvatar;

    /**
     * 说明
     */
    private String userDesc;

    /**
     * 最后一次登录时间
     */
    private Date loginLast;

    /**
     * 0 正常
     * 1 禁用
     * 2 已删除
     */
    private Integer status = 0;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String role;

    public User() {
    }

    public User(String userName, String userDisplayName, String userPass, String idCard, String userAvatar, String userDesc, Date loginLast, Integer status, Date createTime, String role) {
        this.userName = userName;
        this.userDisplayName = userDisplayName;
        this.userPass = userPass;
        this.idCard = idCard;
        this.userAvatar = userAvatar;
        this.userDesc = userDesc;
        this.loginLast = loginLast;
        this.status = status;
        this.createTime = createTime;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public Date getLoginLast() {
        return loginLast;
    }

    public void setLoginLast(Date loginLast) {
        this.loginLast = loginLast;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userDisplayName='" + userDisplayName + '\'' +
                ", userPass='" + userPass + '\'' +
                ", idCard='" + idCard + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userDesc='" + userDesc + '\'' +
                ", loginLast=" + loginLast +
                ", status=" + status +
                ", createTime=" + createTime +
                ", role='" + role + '\'' +
                '}';
    }
}
