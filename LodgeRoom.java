package com.customerdeal;

import java.io.Serializable;

public class Customer implements Serializable {
    private int customerId;
    private String name;
    private String contact;
    private SkiLevel level;

    public Customer(int customerId, String name, String contact, SkiLevel level) {
        this.customerId = customerId;
        this.name = name;
        this.contact = contact;
        this.level = level;
    }

    

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public SkiLevel getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId +
                ", Name: " + name +
                ", Contact: " + contact +
                ", Ski Level: " + level;
    }
}