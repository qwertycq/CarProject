import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import task4_8.dto.CarModelDto;
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
import task4_8.service.CarModelServiceImpl;

public class CarModelServiceTest {

    @InjectMocks private CarModelServiceImpl carModelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoad(@TempDir File tempDir) throws IOException {
        File tempFile = getTempFile(tempDir);
        carModelService.load(tempFile.getAbsolutePath());

        List<CarModelDto> carModels = carModelService.getAllCarModels();
        assertEquals(3, carModels.size());
        assertEquals("Toyota", carModels.getFirst().getBrand());
    }

    @Test
    public void testFindById(@TempDir File tempDir) throws IOException {
        File tempFile = getTempFile(tempDir);
        carModelService.load(tempFile.getAbsolutePath());

        Optional<CarModelDto> foundCarModel = carModelService.findById(0L);
        assertTrue(foundCarModel.isPresent());
        assertEquals("Toyota", foundCarModel.get().getBrand());
    }

    @Test
    public void testGetAllCarModels(@TempDir File tempDir) throws IOException {
        File tempFile = getTempFile(tempDir);
        carModelService.load(tempFile.getAbsolutePath());

        List<CarModelDto> carModels = carModelService.getAllCarModels();
        assertEquals(3, carModels.size());
    }

    @Test
    public void testGetAllCarModelsByBrandName(@TempDir File tempDir) throws IOException {
        File tempFile = getTempFile(tempDir);
        carModelService.load(tempFile.getAbsolutePath());

        List<CarModelDto> carModels = carModelService.getAllCarModels("Toyota");
        assertEquals(2, carModels.size());
    }

    @Test
    public void testGetCountCarsGroupByModels(@TempDir File tempDir) throws IOException {
        File tempFile = getTempFile(tempDir);
        carModelService.load(tempFile.getAbsolutePath());

        Map<String, Integer> countCarsGroupByModels = carModelService.getCountCarsGroupByModels();
        assertEquals(2, countCarsGroupByModels.get("Toyota"));
        assertEquals(1, countCarsGroupByModels.get("Honda"));
    }

    private File getTempFile(@TempDir File tempDir) throws IOException {
        File tempFile = new File(tempDir, "test.csv");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(
                    """
                    Brand;Model;Year;Price
                    Toyota;Corolla;2005;RU
                    Honda;Civic;2007;RU
                    Toyota;Camry;2007;RU
                    """);
        }

        return tempFile;
    }
}
