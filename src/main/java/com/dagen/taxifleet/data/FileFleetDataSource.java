package com.dagen.taxifleet.data;

import com.dagen.taxifleet.model.Hatchback;
import com.dagen.taxifleet.model.Minivan;
import com.dagen.taxifleet.model.PassengerCar;
import com.dagen.taxifleet.model.Sedan;
import com.dagen.taxifleet.model.SuvCar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileFleetDataSource implements FleetDataSource {

    private static final String FIELD_SEPARATOR = ";";
    private static final int FIELDS_PER_LINE = 8;

    private final Path filePath;

    public FileFleetDataSource(String filePath) {
        this.filePath = Path.of(filePath);
    }

    @Override
    public List<PassengerCar> loadFleet() {
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new FleetDataException("cannot read fleet data file: " + filePath, e);
        }

        List<PassengerCar> cars = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).strip();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            cars.add(parseLine(line, i + 1));
        }
        return cars;
    }

    private PassengerCar parseLine(String line, int lineNumber) {
        String[] fields = line.split(FIELD_SEPARATOR);
        if (fields.length != FIELDS_PER_LINE) {
            throw new FleetDataException("line " + lineNumber + " has " + fields.length
                    + " fields, expected " + FIELDS_PER_LINE);
        }

        try {
            String type = fields[0].trim().toUpperCase();
            String brand = fields[1].trim();
            String model = fields[2].trim();
            int year = Integer.parseInt(fields[3].trim());
            double price = Double.parseDouble(fields[4].trim());
            double fuelConsumption = Double.parseDouble(fields[5].trim());
            int maxSpeed = Integer.parseInt(fields[6].trim());
            String plate = fields[7].trim();

            return switch (type) {
                case "SEDAN" -> new Sedan(brand, model, year, price, fuelConsumption, maxSpeed, plate);
                case "HATCHBACK" -> new Hatchback(brand, model, year, price, fuelConsumption, maxSpeed, plate);
                case "SUV" -> new SuvCar(brand, model, year, price, fuelConsumption, maxSpeed, plate);
                case "MINIVAN" -> new Minivan(brand, model, year, price, fuelConsumption, maxSpeed, plate);
                default -> throw new FleetDataException("line " + lineNumber + " has unknown car type: " + type);
            };
        } catch (NumberFormatException e) {
            throw new FleetDataException("line " + lineNumber + " has an invalid number", e);
        } catch (IllegalArgumentException e) {
            throw new FleetDataException("line " + lineNumber + " is invalid: " + e.getMessage(), e);
        }
    }
}
