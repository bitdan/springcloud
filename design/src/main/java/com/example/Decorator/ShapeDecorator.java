package com.example.Decorator;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 22:36
 */
public  abstract class ShapeDecorator implements  Shape{
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public void draw() {

    }
}
