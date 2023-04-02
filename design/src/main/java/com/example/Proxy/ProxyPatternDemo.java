package com.example.Proxy;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-27 0:09
 */
public class ProxyPatternDemo {
    public static void main(String[] args) {
        House house = new Xiaoming();
        HouseProxy houseProxy = new HouseProxy(house);
        houseProxy.findHouse();
    }
}
