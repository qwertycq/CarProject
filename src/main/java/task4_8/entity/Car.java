package task4_8.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Car {
    private Long id;

    private CarModel carModel;

    private Dealer dealer;

    private String state;

    private String equipment;

    private String color;

    private Double price;
}