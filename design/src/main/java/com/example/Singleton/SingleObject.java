package com.example.Singleton;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 16:07
 */
public class SingleObject {

    private  SingleObject() {
    }
    private  static final SingleObject instace=new SingleObject();

    public static SingleObject getInstace() {
        return instace;
    }
    public void showmsg(){
        System.out.println("hhhh");
    }
}
