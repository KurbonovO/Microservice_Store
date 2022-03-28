package com.example.store.demo.entity;

public class OrderItem {
    private Integer itemId;
    private Integer qty;
    private Double price;

    public OrderItem(){}

    public OrderItem(Integer itemId, Integer qty, Double price) {
        this.itemId = itemId;
        this.qty = qty;
        this.price = price;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId=" + itemId +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
