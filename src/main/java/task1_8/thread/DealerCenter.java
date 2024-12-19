package task1_8.thread;

import task1_8.dto.CarDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DealerCenter {
    private Long id;

    private int countShowroomCars;

    private int countCars;

    private List<CarDto> cars;

    private List<CarDto> carsInShowRoom;
}
