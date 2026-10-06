package com.dagen.taxifleet.model;

public abstract class PassengerCar {

    private final String brand;
    private final String model;
    private final int manufactureYear;
    private final double price;
    private final double fuelConsumption;
    private final int maxSpeed;
    private final String plateNumber;
    private final int seatCount;

    protected PassengerCar(String brand, String model, int manufactureYear, double price,
                            double fuelConsumption, int maxSpeed, String plateNumber, int seatCount) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("brand must not be blank");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("model must not be blank");
        }
        if (plateNumber == null || plateNumber.isBlank()) {
            throw new IllegalArgumentException("plate number must not be blank");
        }
        if (manufactureYear < 1950 || manufactureYear > 2100) {
            throw new IllegalArgumentException("manufacture year out of range: " + manufactureYear);
        }
        if (price < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        if (fuelConsumption <= 0) {
            throw new IllegalArgumentException("fuel consumption must be positive");
        }
        if (maxSpeed <= 0) {
            throw new IllegalArgumentException("max speed must be positive");
        }
        if (seatCount <= 0) {
            throw new IllegalArgumentException("seat count must be positive");
        }

        this.brand = brand;
        this.model = model;
        this.manufactureYear = manufactureYear;
        this.price = price;
        this.fuelConsumption = fuelConsumption;
        this.maxSpeed = maxSpeed;
        this.plateNumber = plateNumber;
        this.seatCount = seatCount;
    }

    public abstract String getBodyType();

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public double getPrice() {
        return price;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public int getSeatCount() {
        return seatCount;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s, %d р., %d місць, %.1f л/100км, до %d км/год, %.0f грн, держ. номер %s",
                getBodyType(), brand, model, manufactureYear, seatCount, fuelConsumption, maxSpeed, price, plateNumber);
    }
}
