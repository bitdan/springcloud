package com.example.Template;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 16:42
 */
public class TemplatePatternDemo {
    public static void main(String[] args) {
        Game game = new Cricket();
        game.play();
        System.out.println("--------------");
        game = new Football();
        game.play();
    }
}
