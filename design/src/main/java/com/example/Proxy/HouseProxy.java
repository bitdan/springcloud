package com.example.Proxy;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-27 0:07
 */
public class HouseProxy implements House{
    private final House house;

    public HouseProxy(House house) {
        this.house = house;
    }

    @Override
    public void findHouse() {
        System.out.println("日志: 代理找房子hhhhh");
        this.house.findHouse();
    }
}
