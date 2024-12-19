package task1_8.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CarEntity {
    private Long id;

    private CarModelEntity carModelEntity;

    private DealerEntity dealerEntity;

    private String state;

    private String equipment;

    private String color;

    private Double price;
}