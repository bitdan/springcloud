package com.example.Template;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 16:40
 */
public class Cricket extends Game {
    @Override
    void init() {
        System.out.println("Cricket Game Initialized! Start playing.");
    }

    @Override
    void startplay() {
        System.out.println("Cricket Game Started. Enjoy the game!");
    }

    @Override
    void entplay() {
        System.out.println("Cricket Game Finished!");
    }
}
