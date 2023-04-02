package com.example.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-26 23:12
 */
public class NewspaperOffice {
    private final List<Customer> customers=new ArrayList<>();
   public void addCustomer(Customer customer){
       customers.add(customer);
   }
   public  void  notifyAllObservers(){
       for (Customer customer : customers) {
           customer.update();
       }
   }

   public  void  newspaper(){
       this.notifyAllObservers();
   }
}
