package com.example.Observer;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 23:09
 */
public class CustomerA extends  Customer{
    @Override
    public void update() {
        System.out.println("客户A的报纸已送达");
    }
}
