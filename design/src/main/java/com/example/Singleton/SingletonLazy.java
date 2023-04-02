package com.example.Singleton;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 16:14
 */
public class SingletonLazy {
    private SingletonLazy() {
    }
    private static  SingletonLazy instace;

    public static synchronized SingletonLazy getInstace() {
        if (instace == null) {
            instace = new SingletonLazy();
        }
        return instace;
    }
}
