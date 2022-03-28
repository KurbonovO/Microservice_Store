package com.example.order.demo.rest;

import com.example.order.demo.entity.Order;
import com.example.order.demo.repo.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrdersController {
    private Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private OrderRepository repo;

    @GetMapping("/orders/all")
    public List<Order> getAllOrders(){

        return repo.getAll();
    }

    @PostMapping("/orders/create")
    public Order placeOrder(@RequestBody Order order){
        logger.info(order.toString());
        repo.createOrder(order);
        return order;
    }
}
