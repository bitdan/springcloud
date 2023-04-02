package com.example.Decorator;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 22:45
 */
public abstract class ClothesDecorator implements Person{
    protected  Person person;

    public ClothesDecorator(Person person) {
        this.person = person;
    }
}
