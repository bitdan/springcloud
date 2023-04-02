package com.example.Observer;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 23:11
 */
public class CustomerB extends  Customer{
    @Override
    public void update() {
        System.out.println("客户B的报纸已送达");
    }
}
