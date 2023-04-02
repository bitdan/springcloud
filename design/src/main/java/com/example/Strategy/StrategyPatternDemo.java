package com.example.Strategy;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 16:32
 */
public class StrategyPatternDemo {
    public static void main(String[] args) {
//        Context contextadd = new Context(new OperationAdd());
//        System.out.println("10+5= " + contextadd.doOperation(10, 5));
//        Context contextSub = new Context(new OperationSubtract());
//        System.out.println("10-5= " + contextSub.doOperation(10, 5));
//        Context contextMul = new Context(new OperationMultiply());
//        System.out.println("10*5= " + contextMul.doOperation(10, 5));
        Context context = new Context();
        context.setStrategy(new OperationAdd());
        System.out.println(context.doOperation(10, 5));
    }
}
