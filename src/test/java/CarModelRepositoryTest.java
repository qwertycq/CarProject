import task1_8.databaseH2.H2DatabaseInitializer;
import task1_8.dto.CarModelDto;
import task1_8.entity.CarModelEntity;
import task1_8.mapper.CarMapper;
import task1_8.repository.CarModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CarModelRepositoryTest {
    private CarModelRepository repository;
    private H2DatabaseInitializer databaseInitializer;

    @BeforeEach
    void initializeDatabase() throws SQLException {
        databaseInitializer = new H2DatabaseInitializer("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        databaseInitializer.init();
        Connection connection = databaseInitializer.getConnection();
        repository = new CarModelRepository(connection);
    }

    @Test
    void shouldFindCarModelById() throws SQLException {
        Optional<CarModelEntity> result = repository.findById(1L);

        assertTrue(result.isPresent(), "Expected car model with ID 1 to be present");
    }

    @Test
    void shouldSaveCarModelAndRetrieveIt() throws SQLException {
        CarModelDto newCarModel = CarModelDto.builder()
                .brand("Tesla")
                .model("Model S")
                .countryOrigin("USA")
                .countryCode("US")
                .build();

        repository.save(CarMapper.toEntity(newCarModel));

    }
}
