package com.hotel.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hotel.system.common.base.BaseEntity;

import java.util.List;


/**
 *
 * 权限，后台的菜单
 * @author liuyanzhao
 * @date 2022/04/05
 */
@TableName("permission")
public class Permission  extends BaseEntity {

    /**
     * 权限名称
     */
    private String name;

    /**
     * pid
     */
    private Long pid;

    /**
     * 资源类型
     */
    private String resourceType;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 序号(越小越靠前)
     */
    private Double sort;

    /**
     * 级别
     */
    @TableField(exist = false)
    private Integer level;

    /**
     * 子权限列表
     */
    @TableField(exist = false)
    private List<Permission> childPermissions;

    public Permission() {
    }

    public Permission(String name, Long pid, String resourceType, String url, String icon, Double sort, Integer level, List<Permission> childPermissions) {
        this.name = name;
        this.pid = pid;
        this.resourceType = resourceType;
        this.url = url;
        this.icon = icon;
        this.sort = sort;
        this.level = level;
        this.childPermissions = childPermissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getSort() {
        return sort;
    }

    public void setSort(Double sort) {
        this.sort = sort;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Permission> getChildPermissions() {
        return childPermissions;
    }

    public void setChildPermissions(List<Permission> childPermissions) {
        this.childPermissions = childPermissions;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "name='" + name + '\'' +
                ", pid=" + pid +
                ", resourceType='" + resourceType + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", sort=" + sort +
                ", level=" + level +
                ", childPermissions=" + childPermissions +
                '}';
    }
}