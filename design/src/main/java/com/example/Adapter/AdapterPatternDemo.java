package com.example.Adapter;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-27 0:00
 */
public class AdapterPatternDemo {
    public static void main(String[] args) {
        Playeradapter playeradapter = new Playeradapter();
        playeradapter.play("mp3","xxx.mp3");
        playeradapter.play("wma","xxx.wma");
    }
}
