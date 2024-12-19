import task4_8.databaseH2.H2DatabaseInitializer;
import task4_8.dto.CarModelDto;
import task4_8.entity.CarModel;
import task4_8.mapper.CarMapper;
import task4_8.repository.CarModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RepoTest {
    private CarModelRepository carModelRepository;
    private H2DatabaseInitializer dbInitializer;

    @BeforeEach
    public void setUp() throws SQLException {
        dbInitializer = new H2DatabaseInitializer("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        dbInitializer.drop();
        dbInitializer.init();
        Connection connection = dbInitializer.getConnection();
        carModelRepository = new CarModelRepository(connection);
    }

    @Test
    public void testFindById() throws SQLException {
        Optional<CarModel> carModelOptional = carModelRepository.findById(1L);

        assertEquals(true, carModelOptional.isPresent());
    }

    @Test
    public void testSave() throws SQLException {
        CarModelDto carModelDto = CarModelDto.builder()
                .brand("Toyota")
                .model("Corolla")
                .countryOrigin("Japan")
                .countryCode("JP")
                .build();

        carModelRepository.save(CarMapper.toEntity(carModelDto));

        Optional<CarModel> carModelOptional = carModelRepository.findById(5L);
        assertTrue(carModelOptional.isPresent());
        assertEquals("Toyota", carModelOptional.get().getBrand());
    }
}
