package com.customerdeal;

import java.io.Serializable;

public abstract class Accommodation implements Pricable, Serializable {
    private int id;
    private String name;
    private double pricePerNight;
    private boolean availability;
    private AccommodationType type;

    public Accommodation(int id, String name, double pricePerNight, boolean availability, AccommodationType type) {
        this.id = id;
        this.name = name;
        this.pricePerNight = pricePerNight;
        this.availability = availability;
        this.type = type;


    }

    public int getid() {
        return id;
    }
    public String getName() {
        return name;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailable(boolean available) {
        this.availability = available;
    }

    public AccommodationType getType() {
        return type;
    }

    @Override
    public double calculatePrice() {
        return pricePerNight;
    
    }
    @Override
    public String toString() {
        return "ID: " + id +
        ", Name: " + name +
                ", Type: " + type +
                ", Price per night: $" + pricePerNight +
                ", Available: " + availability;

    }


}



