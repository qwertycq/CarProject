package task1_8.repository;

import task1_8.entity.CarModelEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarModelRepository {

    private final Connection connection;

    public CarModelRepository(Connection connection) {
        this.connection = connection;
    }

    public List<CarModelEntity> findAll() throws SQLException {
        String query = "SELECT * FROM car_model";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet result = stmt.executeQuery()) {
            List<CarModelEntity> models = new ArrayList<>();
            while (result.next()) {
                models.add(mapToCarModel(result));
            }
            return models;
        }
    }

    public Optional<CarModelEntity> findById(Long id) throws SQLException {
        String query = "SELECT * FROM car_model WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return Optional.of(mapToCarModel(result));
            }
            return Optional.empty();
        }
    }

    public void save(CarModelEntity carModelEntity) throws SQLException {
        String query = "INSERT INTO car_model (brand, model, country_origin, country_code) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, carModelEntity.getBrand());
            stmt.setString(2, carModelEntity.getModel());
            stmt.setString(3, carModelEntity.getCountryOrigin());
            stmt.setString(4, carModelEntity.getCountryCode());
            stmt.executeUpdate();
        }
    }

    public void update(CarModelEntity carModelEntity) throws SQLException {
        String query = "UPDATE car_model SET brand = ?, model = ?, country_origin = ?, country_code = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, carModelEntity.getBrand());
            stmt.setString(2, carModelEntity.getModel());
            stmt.setString(3, carModelEntity.getCountryOrigin());
            stmt.setString(4, carModelEntity.getCountryCode());
            stmt.setLong(5, carModelEntity.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteById(Long id) throws SQLException {
        String query = "DELETE FROM car_model WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private CarModelEntity mapToCarModel(ResultSet resultSet) throws SQLException {
        return CarModelEntity.builder()
                .id(resultSet.getLong("id"))
                .brand(resultSet.getString("brand"))
                .model(resultSet.getString("model"))
                .countryOrigin(resultSet.getString("country_origin"))
                .countryCode(resultSet.getString("country_code"))
                .build();
    }
}
