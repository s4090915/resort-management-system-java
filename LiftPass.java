package com.customerdeal;

public class ApartmentUnit extends Accommodation {
    public ApartmentUnit(int id, String name, double pricePerNight, boolean available) {
        super(id, name, pricePerNight, available, AccommodationType.APARTMENT);
    }
}
