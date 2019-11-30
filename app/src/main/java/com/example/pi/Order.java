package com.example.pi;

public class Order {

    private String ordererName;
    private String location;
    private String orderItem;

    public Order(){

    }

    public Order(String ordererName, String location, String orderItem) {
        this.ordererName = ordererName;
        this.location = location;
        this.orderItem = orderItem;
    }

    public String getOrdererName() {
        return ordererName;
    }

    public String getLocation() {
        return location;
    }

    public String getOrderItem() {
        return orderItem;
    }
}
