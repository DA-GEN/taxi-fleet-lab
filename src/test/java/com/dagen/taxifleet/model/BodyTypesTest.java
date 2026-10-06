package com.dagen.taxifleet.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BodyTypesTest {

    @Test
    void hatchbackReportsOwnBodyType() {
        Hatchback car = new Hatchback("Skoda", "Fabia", 2020, 390000, 6.0, 180, "AA5678CC");
        assertEquals("Хетчбек", car.getBodyType());
        assertEquals(4, car.getSeatCount());
    }

    @Test
    void suvReportsOwnBodyType() {
        SuvCar car = new SuvCar("Toyota", "RAV4", 2022, 980000, 8.9, 195, "AA9012DD");
        assertEquals("Позашляховик", car.getBodyType());
        assertEquals(5, car.getSeatCount());
    }

    @Test
    void minivanReportsOwnBodyType() {
        Minivan car = new Minivan("Volkswagen", "Caddy", 2019, 520000, 8.4, 170, "AA3456EE");
        assertEquals("Мінівен", car.getBodyType());
        assertEquals(7, car.getSeatCount());
    }
}
