import task4_8.dto.CarDto;
import task4_8.thread.DealerCenter;
import task4_8.thread.DealerService;
import task4_8.thread.DealerServiceImpl;
import task4_8.utility.CarGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ThreadTest {

    DealerService dealerService;

    @Test
    public void testMultiThread() throws InterruptedException {
        dealerService = new DealerServiceImpl();
        List<CarDto> cars = CarGenerator.generateCars(6000);
        DealerCenter dealerCenter = new DealerCenter();

        long startTimeSingleThread = System.currentTimeMillis();
        dealerService.processDealer(dealerCenter, cars);
        long endTimeSingleThread = System.currentTimeMillis();
        long elapsedTimeSingleThread = endTimeSingleThread - startTimeSingleThread;

        System.out.println("Количество автомобилей в шоуруме: " + dealerCenter.getCarsInShowRoom().size());
        System.out.println("Обработка в одном потоке: " + elapsedTimeSingleThread + " мс");


        DealerCenter dealerCenter2 = new DealerCenter();
        List<CarDto> carList1 = cars.subList(0, 2000);
        List<CarDto> carList2 = cars.subList(2000, 4000);
        List<CarDto> carList3 = cars.subList(4000, 6000);

        Thread thread1 = new Thread( () -> {
            dealerService.processDealer(dealerCenter2, carList1);
        });

        Thread thread2 = new Thread( () -> {
            dealerService.processDealer(dealerCenter2, carList2);
        });

        Thread thread3 = new Thread( () -> {
            dealerService.processDealer(dealerCenter2, carList3);
        });

        long start = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
        long stop = System.currentTimeMillis();
        long duration = stop - start;

        System.out.println("Количество автомобилей в шоуруме: " + dealerCenter.getCarsInShowRoom().size());
        System.out.println("Обработка в 3 потоках: " + duration + " мс");
    }
}
