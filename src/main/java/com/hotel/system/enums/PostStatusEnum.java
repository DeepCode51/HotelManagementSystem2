package com.hotel.system.enums;

/**
 * <pre>
 *     客房状态enum
 * </pre>
 * @author: wpx
 * @date: 2022/04/30
 */
public enum PostStatusEnum {

    /**
     * 正常
     */
    PUBLISHED(0),

    /**
     * 下架
     */
    DRAFT(1),

    /**
     * 回收站
     */
    RECYCLE(2);

    private Integer code;

    PostStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
