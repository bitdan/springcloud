package com.example.mybatis_plus.annotation;

import java.lang.annotation.*;

/**
 * @author duran
 * @description TODO
 * @date 2023-03-17 23:15
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLog {
    String value() default "";
}