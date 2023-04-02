package com.example.mybatis_plus.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author duran
 * @description TODO
 * @date 2023-01-06 23:24
 */
public enum AgeEnum implements IEnum<Integer> {
    one(1, "一岁"),
    two(2, "二岁"),
    THREE(3, "三岁");

    AgeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

    @Override
    public Integer getValue() {
        return this.code;
    }
}
