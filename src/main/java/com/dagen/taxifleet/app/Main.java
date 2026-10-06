package com.dagen.taxifleet.app;

import com.dagen.taxifleet.data.FileFleetDataSource;
import com.dagen.taxifleet.data.FleetDataException;
import com.dagen.taxifleet.model.PassengerCar;
import com.dagen.taxifleet.service.TaxiPark;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public final class Main {

    private static final String DATA_FILE = "data/fleet.txt";

    private Main() {
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("uk-UA"));

        TaxiPark park;
        try {
            park = new TaxiPark("Файна Таксі", new FileFleetDataSource(DATA_FILE));
        } catch (FleetDataException e) {
            System.out.println("Не вдалося завантажити автопарк: " + e.getMessage());
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            runMenu(park, scanner);
        }
    }

    private static void runMenu(TaxiPark park, Scanner scanner) {
        boolean running = true;
        while (running) {
            printMenu(park);
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> printFleet(park.getCars());
                case "2" -> System.out.printf("Загальна вартість автопарку: %.2f грн%n", park.getTotalFleetCost());
                case "3" -> printFleet(park.sortedByFuelConsumption());
                case "4" -> findBySpeedRange(park, scanner);
                case "0" -> running = false;
                default -> System.out.println("Такого пункту меню немає.");
            }
        }
        System.out.println("До побачення!");
    }

    private static void printMenu(TaxiPark park) {
        System.out.println();
        System.out.println("=== " + park.getCompanyName() + " ===");
        System.out.println("1. Показати весь автопарк");
        System.out.println("2. Порахувати вартість автопарку");
        System.out.println("3. Відсортувати автомобілі за витратою палива");
        System.out.println("4. Знайти автомобілі за діапазоном швидкості");
        System.out.println("0. Вихід");
        System.out.print("Ваш вибір: ");
    }

    private static void printFleet(List<PassengerCar> cars) {
        if (cars.isEmpty()) {
            System.out.println("Автопарк порожній.");
            return;
        }
        for (PassengerCar car : cars) {
            System.out.println(car);
        }
    }

    private static void findBySpeedRange(TaxiPark park, Scanner scanner) {
        try {
            System.out.print("Мінімальна швидкість (км/год): ");
            int min = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Максимальна швидкість (км/год): ");
            int max = Integer.parseInt(scanner.nextLine().trim());
            printFleet(park.findBySpeedRange(min, max));
        } catch (NumberFormatException e) {
            System.out.println("Швидкість потрібно вводити числом.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
