package com.hotel.system.enums;

/**
 * <pre>
 *     角色枚举
 * </pre>
 * @author: wpx
 * @date: 2022/04/30
 */
public enum RoleEnum {

    /**
     * 管理员
     */
    ADMIN("admin"),

    /**
     * 客户
     */
    CUSTOMER("customer"),

    /**
     * 工作人员
     */
    WORKER("worker"),

    /**
     * 清洁人员
     */
    CLEANER("cleaner");

    private String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
