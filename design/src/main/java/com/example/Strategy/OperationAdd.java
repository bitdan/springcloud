package com.example.Strategy;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 16:28
 */
public class OperationAdd implements Strategy {

    @Override
    public int doOperation(int num1, int num2) {
        return num1+num2;
    }
}
