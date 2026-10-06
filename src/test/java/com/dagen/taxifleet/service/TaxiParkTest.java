package com.dagen.taxifleet.service;

import com.dagen.taxifleet.data.FleetDataSource;
import com.dagen.taxifleet.model.Hatchback;
import com.dagen.taxifleet.model.PassengerCar;
import com.dagen.taxifleet.model.Sedan;
import com.dagen.taxifleet.model.SuvCar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaxiParkTest {

    @Mock
    private FleetDataSource dataSource;

    private Sedan sedan;
    private Hatchback hatchback;
    private SuvCar suv;

    @BeforeEach
    void setUp() {
        sedan = new Sedan("Toyota", "Camry", 2019, 680000, 7.5, 210, "AA1234BB");
        hatchback = new Hatchback("Skoda", "Fabia", 2020, 390000, 6.0, 180, "AA5678CC");
        suv = new SuvCar("Toyota", "RAV4", 2022, 980000, 8.9, 195, "AA9012DD");

        when(dataSource.loadFleet()).thenReturn(List.of(sedan, hatchback, suv));
    }

    @Test
    void sumsPriceOfEveryCarInThePark() {
        TaxiPark park = new TaxiPark("Файна Таксі", dataSource);

        assertEquals(680000 + 390000 + 980000, park.getTotalFleetCost());
    }

    @Test
    void sortsCarsByFuelConsumptionAscending() {
        TaxiPark park = new TaxiPark("Файна Таксі", dataSource);

        List<PassengerCar> sorted = park.sortedByFuelConsumption();

        assertEquals(List.of(hatchback, sedan, suv), sorted);
    }

    @Test
    void findsCarsWithinGivenSpeedRange() {
        TaxiPark park = new TaxiPark("Файна Таксі", dataSource);

        List<PassengerCar> found = park.findBySpeedRange(185, 210);

        assertEquals(List.of(sedan, suv), found);
    }

    @Test
    void rejectsSpeedRangeWhereMinIsGreaterThanMax() {
        TaxiPark park = new TaxiPark("Файна Таксі", dataSource);

        assertThrows(IllegalArgumentException.class, () -> park.findBySpeedRange(200, 100));
    }

    @Test
    void addingACarGrowsFleetAndTotalCost() {
        TaxiPark park = new TaxiPark("Файна Таксі", dataSource);
        double costBefore = park.getTotalFleetCost();

        park.addCar(new Hatchback("Renault", "Clio", 2018, 310000, 5.6, 175, "AA7788RN"));

        assertEquals(4, park.getCars().size());
        assertTrue(park.getTotalFleetCost() > costBefore);
    }
}
