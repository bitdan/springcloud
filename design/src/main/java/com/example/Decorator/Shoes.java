package com.example.Decorator;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 22:58
 */
public class Shoes extends ClothesDecorator{
    public Shoes(Person person) {
        super(person);
    }

    @Override
    public Double cost() {
        return this.person.cost()+1200;
    }

    @Override
    public void show() {
        this.person.show();
        System.out.println("买了鞋子, 累计消费" + this.cost());
    }
}
