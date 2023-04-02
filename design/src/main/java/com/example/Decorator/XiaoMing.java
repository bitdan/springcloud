package com.example.Decorator;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 22:44
 */
public class XiaoMing implements Person{
    @Override
    public Double cost() {
        return 0.0;
    }

    @Override
    public void show() {
        System.out.println("啥都没得的小明");
    }
}
