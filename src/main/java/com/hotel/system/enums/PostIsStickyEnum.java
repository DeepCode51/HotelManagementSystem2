package com.hotel.system.enums;

/**
 * 客房置顶枚举
 * @author : wpx
 * @date : 2022/04/30
 */
public enum PostIsStickyEnum {

    /**
     * 真
     */
    TRUE(1),

    /**
     * 假
     */
    FALSE(0);

    private Integer value;

    PostIsStickyEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
