package task1_8.repository;

import task1_8.entity.CarEntity;
import task1_8.entity.CarModelEntity;
import task1_8.entity.DealerEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarRepository {

    private final Connection connection;

    public CarRepository(Connection connection) {
        this.connection = connection;
    }

    public List<CarEntity> findAll() throws SQLException {
        String query = "SELECT c.*, cm.*, d.* FROM car c " +
                "JOIN car_model cm ON c.car_model_id = cm.id " +
                "JOIN dealer d ON c.dealer_id = d.id";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet result = stmt.executeQuery()) {

            List<CarEntity> carEntities = new ArrayList<>();
            while (result.next()) {
                carEntities.add(mapToCar(result));
            }
            return carEntities;
        }
    }

    public Optional<CarEntity> findById(Long id) throws SQLException {
        String query = "SELECT c.*, cm.*, d.* FROM car c " +
                "JOIN car_model cm ON c.car_model_id = cm.id " +
                "JOIN dealer d ON c.dealer_id = d.id " +
                "WHERE c.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return Optional.of(mapToCar(result));
            }
            return Optional.empty();
        }
    }

    public void save(CarEntity carEntity) throws SQLException {
        String query = "INSERT INTO car (car_model_id, dealer_id, state, equipment, color, price) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, carEntity.getCarModelEntity().getId());
            stmt.setLong(2, carEntity.getDealerEntity().getId());
            stmt.setString(3, carEntity.getState());
            stmt.setString(4, carEntity.getEquipment());
            stmt.setString(5, carEntity.getColor());
            stmt.setDouble(6, carEntity.getPrice());
            stmt.executeUpdate();
        }
    }

    public void update(CarEntity carEntity) throws SQLException {
        String query = "UPDATE car SET car_model_id = ?, dealer_id = ?, state = ?, equipment = ?, color = ?, price = ? " +
                "WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, carEntity.getCarModelEntity().getId());
            stmt.setLong(2, carEntity.getDealerEntity().getId());
            stmt.setString(3, carEntity.getState());
            stmt.setString(4, carEntity.getEquipment());
            stmt.setString(5, carEntity.getColor());
            stmt.setDouble(6, carEntity.getPrice());
            stmt.setLong(7, carEntity.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteById(Long id) throws SQLException {
        String query = "DELETE FROM car WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private CarEntity mapToCar(ResultSet resultSet) throws SQLException {
        CarModelEntity carModelEntity = CarModelEntity.builder()
                .id(resultSet.getLong("cm.id"))
                .brand(resultSet.getString("cm.brand"))
                .model(resultSet.getString("cm.model"))
                .countryOrigin(resultSet.getString("cm.country_origin"))
                .countryCode(resultSet.getString("cm.country_code"))
                .build();

        DealerEntity dealerEntity = DealerEntity.builder()
                .id(resultSet.getLong("d.id"))
                .name(resultSet.getString("d.name"))
                .build();

        return CarEntity.builder()
                .id(resultSet.getLong("c.id"))
                .carModelEntity(carModelEntity)
                .dealerEntity(dealerEntity)
                .state(resultSet.getString("c.state"))
                .equipment(resultSet.getString("c.equipment"))
                .color(resultSet.getString("c.color"))
                .price(resultSet.getDouble("c.price"))
                .build();
    }
}
