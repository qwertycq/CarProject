import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import task1_8.dto.CarModelDto;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import task1_8.service.CarModelServiceImpl;

public class CarEntityModelServiceTest {

    @InjectMocks
    private CarModelServiceImpl carModelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoad(@TempDir File tempDir) throws IOException {
        File tempFile = createTestFile(tempDir);
        carModelService.load(tempFile.getAbsolutePath());

        List<CarModelDto> carModels = carModelService.getAllCarModels();
        assertEquals(3, carModels.size(), "Ожидается 3 модели машин");
        assertEquals("Toyota", carModels.get(0).getBrand(), "Бренд первой машины должен быть Toyota");
    }

    @Test
    public void testFindById(@TempDir File tempDir) throws IOException {
        File tempFile = createTestFile(tempDir);
        carModelService.load(tempFile.getAbsolutePath());

        Optional<CarModelDto> foundCarModel = carModelService.findById(0L);
        assertTrue(foundCarModel.isPresent(), "Модель машины с ID 0 должна существовать");
        assertEquals("Toyota", foundCarModel.get().getBrand(), "Бренд должен быть Toyota");
    }

    @Test
    public void testGetAllCarModels(@TempDir File tempDir) throws IOException {
        File tempFile = createTestFile(tempDir);
        carModelService.load(tempFile.getAbsolutePath());

        List<CarModelDto> carModels = carModelService.getAllCarModels();
        assertEquals(3, carModels.size(), "Ожидается 3 модели машин");
    }

    @Test
    public void testGetAllCarModelsByBrandName(@TempDir File tempDir) throws IOException {
        File tempFile = createTestFile(tempDir);
        carModelService.load(tempFile.getAbsolutePath());

        List<CarModelDto> carModels = carModelService.getAllCarModels("Toyota");
        assertEquals(2, carModels.size(), "Ожидается 2 модели бренда Toyota");
    }

    @Test
    public void testGetCountCarsGroupByModels(@TempDir File tempDir) throws IOException {
        File tempFile = createTestFile(tempDir);
        carModelService.load(tempFile.getAbsolutePath());

        Map<String, Integer> countCarsGroupByModels = carModelService.getCountCarsGroupByModels();
        assertEquals(2, countCarsGroupByModels.get("Toyota"), "Ожидается 2 машины модели Toyota");
        assertEquals(1, countCarsGroupByModels.get("Honda"), "Ожидается 1 машина модели Honda");
    }

    private File createTestFile(@TempDir File tempDir) throws IOException {
        File testFile = new File(tempDir, "test.csv");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(
                    """
                    Brand;Model;Year;Price
                    Toyota;Corolla;2005;RU
                    Honda;Civic;2007;RU
                    Toyota;Camry;2007;RU
                    """);
        }
        return testFile;
    }
}
