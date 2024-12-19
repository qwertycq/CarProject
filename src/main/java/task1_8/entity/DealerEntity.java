package task1_8.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class DealerEntity {
    private Long id;

    private String name;

    private List<CarEntity> carEntities;
}
