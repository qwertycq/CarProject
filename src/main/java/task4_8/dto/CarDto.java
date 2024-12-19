package task4_8.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CarDto {
    private Long id;

    private CarModelDto carModel;

    private DealerDto dealerCenter;

    private String state;

    private String equipment;

    private String color;

    private Double price;
}
