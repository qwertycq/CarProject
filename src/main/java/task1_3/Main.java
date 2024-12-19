package task1_3;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        FileSystemCarModelService carModelService = new FileSystemCarModelService();
        carModelService.load("027_DST_CAR_MODEL.csv");

        CarService carService = new CarService(carModelService);

        Dealership dealership = carService.generateRandomDealership("AutoDealer", 10);
        System.out.println(dealership);

        List<String> uniqueBrands = carService.getUniqueBrands();
        System.out.println("Уникальные марки автомобилей: " + uniqueBrands);

        Map<String, Integer> carCountByBrand = carService.getCarCountByBrand();
        System.out.println("Количество автомобилей по марке: " + carCountByBrand);

        carService.exampleUsage();

        List<CarModelDTO> toyotaCars = carService.getAllCarDTOs("Toyota");
        System.out.println("Все модели BMW: " + toyotaCars);

        Optional<CarModelDTO> foundCar = carService.findCarById(new CarModelDTO("Daewoo", "MATIZ II Automatic", "Корея", "KR"));
        foundCar.ifPresent(car -> System.out.println("Найден автомобиль: " + car));
    }
}
