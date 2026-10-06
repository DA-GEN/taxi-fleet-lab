package com.dagen.taxifleet.data;

import com.dagen.taxifleet.model.PassengerCar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileFleetDataSourceTest {

    @TempDir
    Path tempDir;

    @Test
    void parsesEveryKnownCarTypeFromFile() throws IOException {
        Path file = tempDir.resolve("fleet.txt");
        Files.writeString(file, """
                # comment line should be skipped

                SEDAN;Toyota;Camry;2019;680000;7.5;210;AA1234BB
                HATCHBACK;Skoda;Fabia;2020;390000;6.0;180;AA5678CC
                SUV;Toyota;RAV4;2022;980000;8.9;195;AA9012DD
                MINIVAN;Volkswagen;Caddy;2019;520000;8.4;170;AA3456EE
                """);

        List<PassengerCar> cars = new FileFleetDataSource(file.toString()).loadFleet();

        assertEquals(4, cars.size());
        assertEquals("Седан", cars.get(0).getBodyType());
        assertEquals("Хетчбек", cars.get(1).getBodyType());
        assertEquals("Позашляховик", cars.get(2).getBodyType());
        assertEquals("Мінівен", cars.get(3).getBodyType());
        assertEquals("Toyota", cars.get(0).getBrand());
        assertEquals(680000, cars.get(0).getPrice());
    }

    @Test
    void failsOnUnknownCarType() throws IOException {
        Path file = tempDir.resolve("fleet.txt");
        Files.writeString(file, "TRUCK;MAN;TGX;2019;680000;7.5;210;AA1234BB\n");

        FileFleetDataSource source = new FileFleetDataSource(file.toString());

        assertThrows(FleetDataException.class, source::loadFleet);
    }

    @Test
    void failsOnLineWithMissingFields() throws IOException {
        Path file = tempDir.resolve("fleet.txt");
        Files.writeString(file, "SEDAN;Toyota;Camry\n");

        FileFleetDataSource source = new FileFleetDataSource(file.toString());

        assertThrows(FleetDataException.class, source::loadFleet);
    }

    @Test
    void failsWhenFileDoesNotExist() {
        FileFleetDataSource source = new FileFleetDataSource(tempDir.resolve("missing.txt").toString());

        assertThrows(FleetDataException.class, source::loadFleet);
    }
}
