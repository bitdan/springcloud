package com.example.Factory;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 23:43
 */
public class FactoryPatternDemo {
    public static void main(String[] args) {
        ComputerFactory factory = new ComputerFactory();
        Computer a = factory.createComputer("a");
        Computer b = factory.createComputer("b");
    }
}
