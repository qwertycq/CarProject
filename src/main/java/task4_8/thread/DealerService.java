package task4_8.thread;


import task4_8.dto.CarDto;

import java.util.List;

public interface DealerService {
    void processDealer(DealerCenter dealer, List<CarDto> cars);
}
