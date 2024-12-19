package task1_8.service;

import task1_8.dto.CarModelDto;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@RequiredArgsConstructor
public class CarModelServiceImpl implements CarModelService {
    private final List<CarModelDto> carModels = new ArrayList<>();
    private Long id = 0L;

    @Override
    public void load(String p) {
        Path path = Paths.get(p);

        try (CSVParser parser =
                CSVFormat.DEFAULT.withDelimiter(';').parse(new FileReader(path.toFile()))) {
            Iterator<CSVRecord> iterator = parser.iterator();
            if (iterator.hasNext()) {
                iterator.next();
            }

            for (CSVRecord record : parser) {
                CarModelDto carModel =
                        new CarModelDto(
                                id++, record.get(0), record.get(1), record.get(2), record.get(3));
                carModels.add(carModel);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<CarModelDto> findById(Long id) {
        return carModels.stream().filter(carModel -> carModel.getId().equals(id)).findFirst();
    }

    @Override
    public List<CarModelDto> getAllCarModels() {
        return new ArrayList<>(carModels);
    }

    @Override
    public List<CarModelDto> getAllCarModels(String brandName) {
        return carModels.stream()
                .filter(carModel -> carModel.getBrand().equals(brandName))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> getCountCarsGroupByModels() {
        return carModels.stream()
                .collect(
                        Collectors.groupingBy(
                                CarModelDto::getBrand, Collectors.summingInt(c -> 1)));
    }
}
