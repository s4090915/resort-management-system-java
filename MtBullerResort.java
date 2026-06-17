package com.customerdeal;

public class HotelRoom extends Accommodation {
    public HotelRoom(int id, String name, double pricePerNight, boolean available) {
        super(id, name, pricePerNight, available, AccommodationType.HOTEL);
}

}
