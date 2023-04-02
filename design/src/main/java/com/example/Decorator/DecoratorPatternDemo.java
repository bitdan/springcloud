package com.example.Decorator;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 22:40
 */
public class DecoratorPatternDemo {
    public static void main(String[] args) {
        Circle circle = new Circle();
        RedShapeDecorator redCircle = new RedShapeDecorator(new Circle());
        RedShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());
        System.out.println("Circle with normal border");
        redCircle.draw();
        System.out.println("\nCircle of red border");
        redCircle.draw();

        System.out.println("\nRectangle of red border");
        redRectangle.draw();

    }
}
