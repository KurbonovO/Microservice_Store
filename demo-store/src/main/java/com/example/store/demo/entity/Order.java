package com.example.store.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private List<OrderItem> items  = new ArrayList<>();
    private Double total = 0.0d;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void addItem(OrderItem orderItem){
        this.total = total + (orderItem.getQty() * orderItem.getPrice());
        this.items.add(orderItem);
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", items=" + items +
                ", total=" + total +
                '}';
    }
}
