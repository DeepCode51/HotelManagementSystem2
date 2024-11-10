package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;


/**
 * 用户和角色关联
 * @author liuyanzhao
 * @date 2022/04/05
 */
@TableName("user_role_ref")
public class UserRoleRef  extends BaseEntity {


    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 角色Id
     */
    private Long roleId;

    public UserRoleRef(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRoleRef() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRoleRef{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}