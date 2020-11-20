package service;

import integration.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service("ordersStorage")
public class OrdersStorage {
    ArrayList<Order> list = new ArrayList<>();
    public Order process(Order order){
        list.add(order);
        System.out.println(Arrays.asList(list));
        return order;
    }
}
