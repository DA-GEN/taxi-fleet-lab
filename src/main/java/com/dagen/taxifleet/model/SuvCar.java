package com.dagen.taxifleet.model;

public class SuvCar extends PassengerCar {

    private static final int DEFAULT_SEAT_COUNT = 5;

    public SuvCar(String brand, String model, int manufactureYear, double price,
                   double fuelConsumption, int maxSpeed, String plateNumber) {
        super(brand, model, manufactureYear, price, fuelConsumption, maxSpeed, plateNumber, DEFAULT_SEAT_COUNT);
    }

    @Override
    public String getBodyType() {
        return "Позашляховик";
    }
}
