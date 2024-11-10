package com.hotel.system.enums;

/**
 * <pre>
 *     订单状态enum
 * </pre>
 * @author : wpx
 * @date : 2022/04/30
 */
public enum OrderStatusEnum {

    /**
     * 待支付
     */
    NOT_PAY(0),

    /**
     * 已支付
     */
    HAS_PAY(1),

    /**
     * 已退房
     */
    FINISHED(2),

    /**
     * 已取消
     */
    CANCEL(3),

    /**
     * 支付超时
     */
    PAY_CANCEL(4)

    ;

    private Integer code;

    OrderStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
