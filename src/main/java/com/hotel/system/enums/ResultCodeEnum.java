package com.hotel.system.enums;

/**
 * 返回结果enum
 * @author liuyanzhao
 * @date 2022-03-16
 */
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(1),

    /**
     * 失败
     */
    FAIL(0);

    Integer code;

    ResultCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
