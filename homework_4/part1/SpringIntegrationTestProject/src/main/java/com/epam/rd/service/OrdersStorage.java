package com.epam.rd.service;

import com.epam.rd.integration.Order;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class OrdersStorage {
    private final Logger logger = LogManager.getLogger(OrdersStorage.class);
    List<Order> list = new ArrayList<>();
    public Order process(Order order){
        list.add(order);
        logger.info(Arrays.asList(list).toString());
        return order;
    }
}
