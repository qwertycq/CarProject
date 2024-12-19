package task4_8;

import task4_8.service.CarModelService;
import task4_8.service.CarModelServiceImpl;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        final CarModelService carModelService = new CarModelServiceImpl();
        carModelService.load("027_DST_CAR_MODEL.csv");
        Map<String, Integer> carsCountByModel = carModelService.getCountCarsGroupByModels();

        for (Map.Entry<String, Integer> entry : carsCountByModel.entrySet()) {
            String k = entry.getKey();
            Integer v = entry.getValue();
            System.out.println(k + ": " + v);
        }
    }
}
