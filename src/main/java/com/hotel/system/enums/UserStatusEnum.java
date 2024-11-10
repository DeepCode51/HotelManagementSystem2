package com.hotel.system.enums;

/**
 * 用户状态enum
 * @author: wpx
 * @date: 2022/04/30
 */
public enum UserStatusEnum {

    /**
     * 正常
     */
    NORMAL(0),

    /**
     * 禁止登录
     */
    BAN(1);


    private Integer code;

    UserStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
