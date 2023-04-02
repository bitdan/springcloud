package com.example.Template;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 16:42
 */
public class Football extends  Game{
    @Override
    void init() {
        System.out.println("Football Game Initialized! Start playing.");
    }

    @Override
    void startplay() {
        System.out.println("Football Game Started. Enjoy the game!");
    }

    @Override
    void entplay() {
        System.out.println("Football Game Finished!");
    }
}
