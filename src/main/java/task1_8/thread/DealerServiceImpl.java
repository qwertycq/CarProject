package task1_8.thread;

import task1_8.dto.CarDto;

import java.util.ArrayList;
import java.util.List;

public class DealerServiceImpl implements DealerService {

    @Override
    public void processDealer(DealerCenter dealer, List<CarDto> cars) {
        dealer.setCarsInShowRoom(new ArrayList<>());

        cars.forEach(car -> {
            if (isNeedAddToShowroom(car)) {
                synchronized (this) {
                    dealer.getCarsInShowRoom().add(car);
                    dealer.setCountShowroomCars(dealer.getCountShowroomCars() + 1);
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean isNeedAddToShowroom(CarDto car) {
        return car.getColor().equals("Чёрный") && car.getEquipment().equals("Спорт");
    }
}
