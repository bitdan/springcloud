package com.example.Template;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 16:40
 */
public abstract class Game {
    abstract void init();
    abstract void startplay();
    abstract void entplay();
    public final void  play(){
        init();
        startplay();
        entplay();
    }
}
