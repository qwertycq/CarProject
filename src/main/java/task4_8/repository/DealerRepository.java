package task4_8.repository;


import task4_8.entity.Car;
import task4_8.entity.CarModel;
import task4_8.entity.Dealer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DealerRepository {

    private final Connection connection;

    public DealerRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Dealer> findAll() throws SQLException {
        String sql = "SELECT d.*, c.*, cm.* FROM dealer d " +
                "LEFT JOIN car c ON d.id = c.dealer_id " +
                "LEFT JOIN car_model cm ON c.car_model_id = cm.id";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Dealer> dealers = new ArrayList<>();
            Dealer currentDealer = null;
            List<Car> cars = null;
            while (rs.next()) {
                long dealerId = rs.getLong("d.id");

                if (currentDealer == null || currentDealer.getId() != dealerId) {
                    if (currentDealer != null) {
                        currentDealer.setCars(cars);
                        dealers.add(currentDealer);
                    }

                    currentDealer = Dealer.builder()
                            .id(dealerId)
                            .name(rs.getString("d.name"))
                            .build();
                    cars = new ArrayList<>();
                }

                if (rs.getLong("c.id") != 0) {
                    CarModel carModel = CarModel.builder()
                            .id(rs.getLong("cm.id"))
                            .brand(rs.getString("cm.brand"))
                            .model(rs.getString("cm.model"))
                            .countryOrigin(rs.getString("cm.country_origin"))
                            .countryCode(rs.getString("cm.country_code"))
                            .build();

                    Car car = Car.builder()
                            .id(rs.getLong("c.id"))
                            .carModel(carModel)
                            .state(rs.getString("c.state"))
                            .equipment(rs.getString("c.equipment"))
                            .color(rs.getString("c.color"))
                            .price(rs.getDouble("c.price"))
                            .build();

                    cars.add(car);
                }
            }

            if (currentDealer != null) {
                currentDealer.setCars(cars);
                dealers.add(currentDealer);
            }

            return dealers;
        }
    }

    public Dealer findById(Long id) throws SQLException {
        String sql = "SELECT d.*, c.*, cm.* FROM dealer d " +
                "LEFT JOIN car c ON d.id = c.dealer_id " +
                "LEFT JOIN car_model cm ON c.car_model_id = cm.id " +
                "WHERE d.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Dealer dealer = null;
                List<Car> cars = new ArrayList<>();
                while (rs.next()) {
                    if (dealer == null) {
                        dealer = Dealer.builder()
                                .id(rs.getLong("d.id"))
                                .name(rs.getString("d.name"))
                                .build();
                    }

                    if (rs.getLong("c.id") != 0) {
                        CarModel carModel = CarModel.builder()
                                .id(rs.getLong("cm.id"))
                                .brand(rs.getString("cm.brand"))
                                .model(rs.getString("cm.model"))
                                .countryOrigin(rs.getString("cm.country_origin"))
                                .countryCode(rs.getString("cm.country_code"))
                                .build();

                        Car car = Car.builder()
                                .id(rs.getLong("c.id"))
                                .carModel(carModel)
                                .state(rs.getString("c.state"))
                                .equipment(rs.getString("c.equipment"))
                                .color(rs.getString("c.color"))
                                .price(rs.getDouble("c.price"))
                                .build();

                        cars.add(car);
                    }
                }
                if (dealer != null) {
                    dealer.setCars(cars);
                }
                return dealer;
            }
        }
    }

    public void save(Dealer dealer) throws SQLException {
        String sql = "INSERT INTO dealer (name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dealer.getName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    dealer.setId(rs.getLong(1));
                }
            }
        }
    }

    public void update(Dealer dealer) throws SQLException {
        String sql = "UPDATE dealer SET name = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dealer.getName());
            ps.setLong(2, dealer.getId());
            ps.executeUpdate();
        }
    }

    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM dealer WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
