package com.hotel.system.enums;

/**
 * true or false enum
 * @author: wpx
 * @date: 2022/04/30
 */
public enum TrueFalseEnum {

    /**
     * 真
     */
    TRUE("true"),

    /**
     * 假
     */
    FALSE("false");

    private String value;

    TrueFalseEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
