package com.example.Singleton;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 16:17
 */
public class Singletonhungry {
    private Singletonhungry() {
    }
    private  static final Singletonhungry instace= new Singletonhungry();

    public static Singletonhungry getInstace() {
        return instace;
    }
}
