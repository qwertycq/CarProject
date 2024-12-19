package task4_8.repository;


import task4_8.entity.Car;
import task4_8.entity.CarModel;
import task4_8.entity.Dealer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarRepository {

    private final Connection connection;

    public CarRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Car> findAll() throws SQLException {
        String sql = "SELECT c.*, cm.*, d.* FROM car c " +
                "JOIN car_model cm ON c.car_model_id = cm.id " +
                "JOIN dealer d ON c.dealer_id = d.id";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Car> cars = new ArrayList<>();
            while (rs.next()) {
                Car car = mapRowToCar(rs);
                cars.add(car);
            }
            return cars;
        }
    }

    public Optional<Car> findById(Long id) throws SQLException {
        String sql = "SELECT c.*, cm.*, d.* FROM car c " +
                "JOIN car_model cm ON c.car_model_id = cm.id " +
                "JOIN dealer d ON c.dealer_id = d.id " +
                "WHERE c.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToCar(rs));
            } else {
                return Optional.empty();
            }
        }
    }

    public void save(Car car) throws SQLException {
        String sql = "INSERT INTO car (car_model_id, dealer_id, state, equipment, color, price) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, car.getCarModel().getId());
            ps.setLong(2, car.getDealer().getId());
            ps.setString(3, car.getState());
            ps.setString(4, car.getEquipment());
            ps.setString(5, car.getColor());
            ps.setDouble(6, car.getPrice());
            ps.executeUpdate();
        }
    }

    public void update(Car car) throws SQLException {
        String sql = "UPDATE car SET car_model_id = ?, dealer_id = ?, state = ?, equipment = ?, color = ?, price = ? " +
                "WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, car.getCarModel().getId());
            ps.setLong(2, car.getDealer().getId());
            ps.setString(3, car.getState());
            ps.setString(4, car.getEquipment());
            ps.setString(5, car.getColor());
            ps.setDouble(6, car.getPrice());
            ps.setLong(7, car.getId());
            ps.executeUpdate();
        }
    }

    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM car WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

        private Car mapRowToCar(ResultSet rs) throws SQLException {
        CarModel carModel = CarModel.builder()
                .id(rs.getLong("cm.id"))
                .brand(rs.getString("cm.brand"))
                .model(rs.getString("cm.model"))
                .countryOrigin(rs.getString("cm.country_origin"))
                .countryCode(rs.getString("cm.country_code"))
                .build();

        Dealer dealer = Dealer.builder()
                .id(rs.getLong("d.id"))
                .name(rs.getString("d.name"))
                .build();

        return Car.builder()
                .id(rs.getLong("c.id"))
                .carModel(carModel)
                .dealer(dealer)
                .state(rs.getString("c.state"))
                .equipment(rs.getString("c.equipment"))
                .color(rs.getString("c.color"))
                .price(rs.getDouble("c.price"))
                .build();
    }
}
