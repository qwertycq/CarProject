package task1_8.mapper;


import task1_8.dto.CarDto;
import task1_8.dto.CarModelDto;
import task1_8.dto.DealerDto;
import task1_8.entity.CarEntity;
import task1_8.entity.CarModelEntity;
import task1_8.entity.DealerEntity;

public class CarMapper {

    public static CarDto toDto(CarEntity carEntity) {
        return CarDto.builder()
                .id(carEntity.getId())
                .carModel(toDto(carEntity.getCarModelEntity()))
                .dealerCenter(toDto(carEntity.getDealerEntity()))
                .state(carEntity.getState())
                .equipment(carEntity.getEquipment())
                .color(carEntity.getColor())
                .price(carEntity.getPrice())
                .build();
    }

    public static CarModelDto toDto(CarModelEntity carModelEntity) {
        return CarModelDto.builder()
                .id(carModelEntity.getId())
                .brand(carModelEntity.getBrand())
                .model(carModelEntity.getModel())
                .countryOrigin(carModelEntity.getCountryOrigin())
                .countryCode(carModelEntity.getCountryCode())
                .build();
    }

    public static DealerDto toDto(DealerEntity dealerEntity) {
        return DealerDto.builder()
                .id(dealerEntity.getId())
                .cars(dealerEntity.getCarEntities().stream().map(CarMapper::toDto).toList())
                .name(dealerEntity.getName())
                .build();
    }

    public static CarEntity toEntity(CarDto carDto) {
        return CarEntity.builder()
                .id(carDto.getId())
                .carModelEntity(toEntity(carDto.getCarModel()))
                .dealerEntity(toEntity(carDto.getDealerCenter()))
                .state(carDto.getState())
                .equipment(carDto.getEquipment())
                .color(carDto.getColor())
                .price(carDto.getPrice())
                .build();
    }

    public static CarModelEntity toEntity(CarModelDto carModelDto) {
        return CarModelEntity.builder()
                .id(carModelDto.getId())
                .brand(carModelDto.getBrand())
                .model(carModelDto.getModel())
                .countryOrigin(carModelDto.getCountryOrigin())
                .countryCode(carModelDto.getCountryCode())
                .build();
    }

    public static DealerEntity toEntity(DealerDto dealerDto) {
        return DealerEntity.builder()
                .id(dealerDto.getId())
                .carEntities(dealerDto.getCars().stream().map(CarMapper::toEntity).toList())
                .name(dealerDto.getName())
                .build();
    }
}
