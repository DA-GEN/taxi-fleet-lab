package com.dagen.taxifleet.service;

import com.dagen.taxifleet.data.FleetDataSource;
import com.dagen.taxifleet.model.PassengerCar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;

public class TaxiPark {

    private final String companyName;
    private final List<PassengerCar> cars;

    public TaxiPark(String companyName, FleetDataSource dataSource) {
        if (companyName == null || companyName.isBlank()) {
            throw new IllegalArgumentException("company name must not be blank");
        }
        this.companyName = companyName;
        this.cars = new ArrayList<>(dataSource.loadFleet());
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<PassengerCar> getCars() {
        return Collections.unmodifiableList(cars);
    }

    public void addCar(PassengerCar car) {
        if (car == null) {
            throw new IllegalArgumentException("car must not be null");
        }
        cars.add(car);
    }

    public double getTotalFleetCost() {
        double total = 0;
        for (PassengerCar car : cars) {
            total += car.getPrice();
        }
        return total;
    }

    public List<PassengerCar> sortedByFuelConsumption() {
        List<PassengerCar> sorted = new ArrayList<>(cars);
        sorted.sort(Comparator.comparingDouble(PassengerCar::getFuelConsumption));
        return sorted;
    }

    public List<PassengerCar> findBySpeedRange(int minSpeed, int maxSpeed) {
        if (minSpeed > maxSpeed) {
            throw new IllegalArgumentException("minSpeed cannot be greater than maxSpeed");
        }
        List<PassengerCar> result = new ArrayList<>();
        for (PassengerCar car : cars) {
            if (car.getMaxSpeed() >= minSpeed && car.getMaxSpeed() <= maxSpeed) {
                result.add(car);
            }
        }
        return result;
    }
}
