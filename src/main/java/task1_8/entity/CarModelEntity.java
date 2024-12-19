package task1_8.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CarModelEntity {
    private Long id;

    private String brand;

    private String model;

    private String countryOrigin;

    private String countryCode;
}