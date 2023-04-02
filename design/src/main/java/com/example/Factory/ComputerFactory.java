package com.example.Factory;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 23:38
 */
public class ComputerFactory {
    public Computer createComputer(String name) {
        Computer computer = null;
        if (name.equalsIgnoreCase("a")) computer = new ComputerA();
        if (name.equalsIgnoreCase("b")) computer = new ComputerB();
        return computer;
    }
}
