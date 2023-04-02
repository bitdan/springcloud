package com.example.Decorator;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 22:53
 */
public class XiaomingDemo {
    public static void main(String[] args) {
        Person xiaoMing = new XiaoMing();
        xiaoMing = new Shirt(xiaoMing);
        xiaoMing = new Trousers(xiaoMing);
        xiaoMing = new Shoes(xiaoMing);
        xiaoMing.show();
        System.out.println("xiaoming共消费" + xiaoMing.cost());
    }
}
