package com.example.Singleton;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 16:21
 */
public class SingletonDCL {
    private SingletonDCL() {
        System.out.println("创建了single对象");
    }
    private  volatile  static  SingletonDCL singletonDCL;

    public static SingletonDCL getSingletonDCL() {
        if (singletonDCL == null) {
            synchronized (SingletonDCL.class){
                if (singletonDCL == null) {
                    singletonDCL=new SingletonDCL();
                }
            }
        }
        return singletonDCL;
    }
}
