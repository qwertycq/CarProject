package task4_8.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class CarModelDto {
    private Long id;

    private String brand;

    private String model;

    private String countryOrigin;

    private String countryCode;
}
