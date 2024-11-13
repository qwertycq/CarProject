package com.example;

import org.example.CarModelDTO;
import org.example.CarService;
import org.example.FileSystemCarModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTest {

    private CarService carService;

    @BeforeEach
    void setUp() {
        List<CarModelDTO> testCars = List.of(
                new CarModelDTO("BMW", "1 Series 3-door", "Германия", "DE"),
                new CarModelDTO("BMW", "1 Series 5-door", "Германия", "DE"),
                new CarModelDTO("Mercedes", "GLЕ 400", "Германия", "DE"),
                new CarModelDTO("Toyota", "Camry", "Япония", "JP"),
                new CarModelDTO("BMW", "X5", "Германия", "DE"),
                new CarModelDTO("Toyota", "Corolla", "Япония", "JP"),
                new CarModelDTO("Mercedes", "C-Class", "Германия", "DE")
        );

        FileSystemCarModelService mockFileSystemService = Mockito.mock(FileSystemCarModelService.class);
        when(mockFileSystemService.getAllCarDTOs()).thenReturn(testCars);

        this.carService = new CarService(mockFileSystemService);
    }

    @Test
    void testGetUniqueBrands() {
        List<String> uniqueBrands = carService.getUniqueBrands();
        assertEquals(3, uniqueBrands.size(), "Должно быть 3 уникальные марки");
        assertTrue(uniqueBrands.contains("BMW"));
        assertTrue(uniqueBrands.contains("Mercedes"));
        assertTrue(uniqueBrands.contains("Toyota"));
    }

    @Test
    void testGetCarCountByBrand() {
        Map<String, Integer> carCountByBrand = carService.getCarCountByBrand();
        assertEquals(3, carCountByBrand.get("BMW"), "Должно быть 3 автомобиля BMW");
        assertEquals(2, carCountByBrand.get("Toyota"), "Должно быть 2 автомобиля Toyota");
        assertEquals(2, carCountByBrand.get("Mercedes"), "Должно быть 2 автомобиля Mercedes");
    }

    @Test
    void testGetAllCarDTOsByBrand() {
        List<CarModelDTO> bmwCars = carService.getAllCarDTOs("BMW");
        assertEquals(3, bmwCars.size(), "Должно быть 3 автомобиля BMW");
        assertTrue(bmwCars.stream().allMatch(car -> car.getBrand().equals("BMW")));
    }

    @Test
    void testFindCarById() {
        CarModelDTO searchCar = new CarModelDTO("BMW", "1 Series 3-door", "Германия", "DE");
        Optional<CarModelDTO> foundCar = carService.findCarById(searchCar);
        assertTrue(foundCar.isPresent(), "Автомобиль должен быть найден");
        assertEquals(searchCar, foundCar.get(), "Найденный автомобиль должен совпадать с искомым");
    }

    @Test
    void testGetCarModelGroupByModel() {
        Map<String, Integer> bmwModelCount = carService.getCarModelGroupByModel("BMW");
        assertEquals(1, bmwModelCount.get("1 Series 3-door"), "Должен быть 1 автомобиль модели '1 Series 3-door'");
        assertEquals(1, bmwModelCount.get("1 Series 5-door"), "Должен быть 1 автомобиль модели '1 Series 5-door'");
        assertEquals(1, bmwModelCount.get("X5"), "Должен быть 1 автомобиль модели 'X5'");
    }
}
