package task4_8.utility;

import task4_8.dto.CarDto;
import task4_8.dto.CarModelDto;
import task4_8.dto.DealerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarGenerator {
    private static final String[] BRANDS = {"Toyota", "Honda", "Ford", "Skoda", "Volkswagen"};
    private static final String[] MODELS = {"Corolla", "Civic", "Mustang", "Octavia", "Golf"};
    private static final String[] COUNTRIES = {"RU", "KZ", "GER", "US", "CH"};
    private static final String[] STATES = {"Не занят", "В пути", "В наличии", "Продан", "Забронирован"};
    private static final String[] EQUIPMENTS = {"Стандарт", "Премиум", "Спорт"};
    private static final String[] COLORS = {"Чёрный", "Синий", "Серебряный", "Красный", "Белый"};
    private static final String[] DEALERS = {"DEALER1", "DEALER2", "DEALER3", "DEALER4", "DEALER5"};
    private static final double MIN_PRICE = 500000;
    private static final double MAX_PRICE = 2000000;

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
