package com.example.Strategy;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 16:31
 */
public class Context {
    private  Strategy strategy;



    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int doOperation(int num1, int num2) {
        return this.strategy.doOperation(num1, num2);
    }
}
