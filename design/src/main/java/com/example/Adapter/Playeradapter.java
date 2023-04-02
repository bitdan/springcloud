package com.example.Adapter;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 23:56
 */
public class Playeradapter implements  MusicPlayer{
    private final Myplayer myplayer;

    public Playeradapter() {
        this.myplayer = new Myplayer();
    }

    @Override
    public void play(String type, String fileName) {
        if (type.equalsIgnoreCase("mp3")) this.myplayer.playMp3(fileName);
        if (type.equalsIgnoreCase("wma")) this.myplayer.playWma(fileName);
    }
}
