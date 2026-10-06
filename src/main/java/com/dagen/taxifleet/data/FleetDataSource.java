package com.dagen.taxifleet.data;

import com.dagen.taxifleet.model.PassengerCar;

import java.util.List;

public interface FleetDataSource {

    List<PassengerCar> loadFleet();
}
