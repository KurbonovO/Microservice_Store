package com.example.store.demo.service;

import com.example.store.demo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;

    public List<Order> getOrders() {
        ResponseEntity<List<Order>> responseEntity =
                restTemplate.exchange("http://localhost:8686/DEMO-ORDERS/orders/all",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>() {
                        });
        List<Order> orders = responseEntity.getBody();
        return orders;
    }

    public void createOrder(Order order) {
        restTemplate.postForEntity("http://localhost:8686/DEMO-ORDERS/orders/create", order, String.class);
    }
}
