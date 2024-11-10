package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;

import java.util.List;

/**
 * 角色
 * @author liuyanzhao
 * @date 2022/04/05
 */
@TableName("role")
public class Role  extends BaseEntity {

    /**
     * 角色名称：admin，author，subscriber
     */
    private String role;

    /**
     * 描述：管理员，作者，订阅者
     */
    private String description;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 用户注册默认角色
     */
    private Integer isRegisterDefault;

    /**
     * 该角色对应的用户数量，非数据库字段
     */
    @TableField(exist = false)
    private Integer count;

    /**
     * 当前角色的权限列表
     */
    @TableField(exist = false)
    private List<Permission> permissions;

    public Role() {
    }

    public Role(String role, String description, Integer level, Integer isRegisterDefault, Integer count, List<Permission> permissions) {
        this.role = role;
        this.description = description;
        this.level = level;
        this.isRegisterDefault = isRegisterDefault;
        this.count = count;
        this.permissions = permissions;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIsRegisterDefault() {
        return isRegisterDefault;
    }

    public void setIsRegisterDefault(Integer isRegisterDefault) {
        this.isRegisterDefault = isRegisterDefault;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                ", description='" + description + '\'' +
                ", level=" + level +
                ", isRegisterDefault=" + isRegisterDefault +
                ", count=" + count +
                ", permissions=" + permissions +
                '}';
    }
}