package com.example.Observer;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 23:16
 */
public class ObserverPatternDemo {
    public static void main(String[] args) {
        NewspaperOffice newspaperOffice = new NewspaperOffice();
        CustomerA customerA = new CustomerA();
        newspaperOffice.addCustomer(customerA);
        CustomerB customerB = new CustomerB();
        newspaperOffice.addCustomer(customerB);
        newspaperOffice.newspaper();
    }
}
