package com.example.Decorator;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 22:37
 */
public class RedShapeDecorator extends ShapeDecorator{

    public RedShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw() {
        super.draw();

    }
    private  void setRedBorder(Shape decoratedShape){
        System.out.println("Border Color: Red");
    }

}
