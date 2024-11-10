package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;

/**
 * 角色与权限关联
 * @author liuyanzhao
 * @date 2022/04/05
 */
@TableName("role_permission_ref")
public class RolePermissionRef  extends BaseEntity {

    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 权限Id
     */
    private Long permissionId;

    public RolePermissionRef() {
    }

    public RolePermissionRef(Long roleId, Long permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "RolePermissionRef{" +
                "roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }
}