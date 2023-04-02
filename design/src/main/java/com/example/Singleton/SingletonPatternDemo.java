package com.example.Singleton;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 16:11
 */
public class SingletonPatternDemo {
    public static void main(String[] args) {
//        SingleObject object = SingleObject.getInstace();
//        object.showmsg();
        for (int i = 0; i < 10; i++) {
//            SingletonDCL.getSingletonDCL();
            new Thread(
                    () -> {
                        SingletonDCL.getSingletonDCL();
                    }
            ).start();
        }
    }
}
