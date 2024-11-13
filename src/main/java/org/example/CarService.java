package org.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;

public class CarService {
    private FileSystemCarModelService carModelService;

    public CarService(FileSystemCarModelService carModelService) {
        this.carModelService = carModelService;
    }

    public Dealership generateRandomDealership(String dealershipName, int numCars) {
        List<CarModelDTO> availableModels = carModelService.getAllCarDTOs();
        List<CarDTO> cars = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numCars; i++) {
            CarModelDTO model = availableModels.get(random.nextInt(availableModels.size()));
            CarDTO.CarStatus status = CarDTO.CarStatus.values()[random.nextInt(CarDTO.CarStatus.values().length)];
            String configuration = "Config" + random.nextInt(10);
            String color = getRandomColor();
            BigDecimal price = BigDecimal.valueOf(20_000 + random.nextDouble() * 50_000);

            CarDTO car = new CarDTO(i + 1, model, null, status, configuration, color, price);
            cars.add(car);
        }

        Dealership dealership = new Dealership(dealershipName, cars);
        cars.forEach(car -> car.setDealership(dealership));
        return dealership;
    }

    private String getRandomColor() {
        String[] colors = {"Red", "Blue", "Black", "White", "Green"};
        return colors[new Random().nextInt(colors.length)];
    }

    public List<String> getUniqueBrands() {
        Set<String> brands = new HashSet<>();
        for (CarModelDTO model : carModelService.getAllCarDTOs()) {
            brands.add(model.getBrand());
        }
        return new ArrayList<>(brands);
    }

    public Map<String, Integer> getCarCountByBrand() {
        Map<String, Integer> countByBrand = new HashMap<>();
        for (CarModelDTO model : carModelService.getAllCarDTOs()) {
            countByBrand.put(model.getBrand(), countByBrand.getOrDefault(model.getBrand(), 0) + 1);
        }
        return countByBrand;
    }

    public List<CarModelDTO> getAllCarDTOs(String brand) {
        List<CarModelDTO> filteredCars = new ArrayList<>();
        for (CarModelDTO model : carModelService.getAllCarDTOs()) {
            if (model.getBrand().equalsIgnoreCase(brand)) {
                filteredCars.add(model);
            }
        }
        return filteredCars;
    }

    public Optional<CarModelDTO> findCarById(CarModelDTO car) {
        return carModelService.getAllCarDTOs().stream()
                .filter(c -> c.equals(car))
                .findFirst();
    }

    public Map<String, Integer> getCarModelGroupByModel(String brand) {
        Map<String, Integer> modelCountMap = new HashMap<>();
        for (CarModelDTO model : carModelService.getAllCarDTOs()) {
            if (model.getBrand().equalsIgnoreCase(brand)) {
                modelCountMap.put(model.getModel(), modelCountMap.getOrDefault(model.getModel(), 0) + 1);
            }
        }
        return modelCountMap;
    }

    public <T> T measureExecutionTime(String methodName, Supplier<T> method) {
        long startTime = System.nanoTime();
        T result = method.get();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println(methodName + " выполнен за: " + duration + " ms");
        return result;
    }

    public void exampleUsage() {
        measureExecutionTime("generateRandomDealership", () -> generateRandomDealership("Sample Dealership", 10));
    }
}
