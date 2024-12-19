package task4_8.mapper;


import task4_8.dto.CarDto;
import task4_8.dto.CarModelDto;
import task4_8.dto.DealerDto;
import task4_8.entity.Car;
import task4_8.entity.CarModel;
import task4_8.entity.Dealer;

public class CarMapper {

    public static CarDto toDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .carModel(toDto(car.getCarModel()))
                .dealerCenter(toDto(car.getDealer()))
                .state(car.getState())
                .equipment(car.getEquipment())
                .color(car.getColor())
                .price(car.getPrice())
                .build();
    }

    public static CarModelDto toDto(CarModel carModel) {
        return CarModelDto.builder()
                .id(carModel.getId())
                .brand(carModel.getBrand())
                .model(carModel.getModel())
                .countryOrigin(carModel.getCountryOrigin())
                .countryCode(carModel.getCountryCode())
                .build();
    }

    public static DealerDto toDto(Dealer dealer) {
        return DealerDto.builder()
                .id(dealer.getId())
                .cars(dealer.getCars().stream().map(CarMapper::toDto).toList())
                .name(dealer.getName())
                .build();
    }

    public static Car toEntity(CarDto carDto) {
        return Car.builder()
                .id(carDto.getId())
                .carModel(toEntity(carDto.getCarModel()))
                .dealer(toEntity(carDto.getDealerCenter()))
                .state(carDto.getState())
                .equipment(carDto.getEquipment())
                .color(carDto.getColor())
                .price(carDto.getPrice())
                .build();
    }

    public static CarModel toEntity(CarModelDto carModelDto) {
        return CarModel.builder()
                .id(carModelDto.getId())
                .brand(carModelDto.getBrand())
                .model(carModelDto.getModel())
                .countryOrigin(carModelDto.getCountryOrigin())
                .countryCode(carModelDto.getCountryCode())
                .build();
    }

    public static Dealer toEntity(DealerDto dealerDto) {
        return Dealer.builder()
                .id(dealerDto.getId())
                .cars(dealerDto.getCars().stream().map(CarMapper::toEntity).toList())
                .name(dealerDto.getName())
                .build();
    }
}
