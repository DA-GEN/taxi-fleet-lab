package com.dagen.taxifleet.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SedanTest {

    @Test
    void storesFieldsPassedToConstructor() {
        Sedan sedan = new Sedan("Toyota", "Camry", 2019, 680000, 7.5, 210, "AA1234BB");

        assertEquals("Toyota", sedan.getBrand());
        assertEquals("Camry", sedan.getModel());
        assertEquals(2019, sedan.getManufactureYear());
        assertEquals(680000, sedan.getPrice());
        assertEquals(7.5, sedan.getFuelConsumption());
        assertEquals(210, sedan.getMaxSpeed());
        assertEquals("AA1234BB", sedan.getPlateNumber());
        assertEquals("Седан", sedan.getBodyType());
        assertEquals(4, sedan.getSeatCount());
    }

    @Test
    void rejectsNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sedan("Toyota", "Camry", 2019, -1, 7.5, 210, "AA1234BB"));
    }

    @Test
    void rejectsZeroFuelConsumption() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sedan("Toyota", "Camry", 2019, 680000, 0, 210, "AA1234BB"));
    }

    @Test
    void rejectsBlankBrand() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sedan(" ", "Camry", 2019, 680000, 7.5, 210, "AA1234BB"));
    }

    @Test
    void rejectsYearOutOfRange() {
        assertThrows(IllegalArgumentException.class,
                () -> new Sedan("Toyota", "Camry", 1899, 680000, 7.5, 210, "AA1234BB"));
    }
}
