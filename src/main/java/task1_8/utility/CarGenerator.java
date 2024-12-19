package task1_8.utility;

import task1_8.dto.CarDto;
import task1_8.dto.CarModelDto;
import task1_8.dto.DealerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarGenerator {
    private static final String[] BRANDS = {"BMW", "Mercedes", "Audi", "Hyundai", "Kia"};
    private static final String[] MODELS = {"X5", "E-Class", "A4", "Tucson", "Sportage"};
    private static final String[] COUNTRIES = {"JP", "KR", "FR", "IT", "ES"};
    private static final String[] STATES = {"На складе", "Доставляется", "В наличии", "Продан", "Забронирован"};
    private static final String[] EQUIPMENTS = {"Базовая", "Люкс", "Спортивная"};
    private static final String[] COLORS = {"Белый", "Серый", "Синий", "Красный", "Зелёный"};
    private static final String[] DEALERS = {"DEALER_A", "DEALER_B", "DEALER_C", "DEALER_D", "DEALER_E"};
    private static final double MIN_PRICE = 400000;
    private static final double MAX_PRICE = 4000000;

    public static DealerDto generateDealerCenterWithCars(int carsCount) {
        DealerDto dealerCenter = new DealerDto();
        List<CarDto> cars = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < carsCount; i++) {
            CarModelDto carModel = generateRandomCarModel();
            CarDto car = CarDto.builder()
                    .carModel(carModel)
                    .state(STATES[random.nextInt(STATES.length)])
                    .equipment(EQUIPMENTS[random.nextInt(EQUIPMENTS.length)])
                    .color(COLORS[random.nextInt(COLORS.length)])
                    .price(MIN_PRICE + (MAX_PRICE - MIN_PRICE) * random.nextDouble())
                    .build();
            cars.add(car);
        }

        dealerCenter.setCars(cars);
        dealerCenter.setName(DEALERS[random.nextInt(DEALERS.length)]);
        return dealerCenter;
    }

    public static List<CarDto> generateCars(int carsCount) {
        List<CarDto> cars = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < carsCount; i++) {
            CarModelDto carModel = generateRandomCarModel();
            CarDto car = CarDto.builder()
                    .carModel(carModel)
                    .state(STATES[random.nextInt(STATES.length)])
                    .equipment(EQUIPMENTS[random.nextInt(EQUIPMENTS.length)])
                    .color(COLORS[random.nextInt(COLORS.length)])
                    .price(MIN_PRICE + (MAX_PRICE - MIN_PRICE) * random.nextDouble())
                    .build();
            cars.add(car);
        }

        return cars;
    }

    private static CarModelDto generateRandomCarModel() {
        Random random = new Random();
        int index = random.nextInt(BRANDS.length);

        return CarModelDto.builder()
                .brand(BRANDS[index])
                .model(MODELS[index])
                .countryOrigin(COUNTRIES[index])
                .build();
    }
}
