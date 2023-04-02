package com.example.mybatis_plus.enums;

/**
 * @author duran
 * @description TODO
 * @date 2023-01-06 22:52
 */
public enum statusEnum {

    WORK(1,"上班"),REST(0,"休息");
    private Integer code;
    private String msg;

    statusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
