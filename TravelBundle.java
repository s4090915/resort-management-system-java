package com.customerdeal;

public class LodgeRoom extends Accommodation {
    public LodgeRoom(int id, String name, double pricePerNight, boolean available) {
        super(id, name, pricePerNight, available, AccommodationType.LODGE);
    }
}


