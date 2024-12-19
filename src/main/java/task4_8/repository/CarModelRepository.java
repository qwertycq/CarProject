package task4_8.repository;

import task4_8.entity.CarModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarModelRepository {

    private final Connection connection;

    public CarModelRepository(Connection connection) {
        this.connection = connection;
    }

    public List<CarModel> findAll() throws SQLException {
        String sql = "SELECT * FROM car_model";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<CarModel> carModels = new ArrayList<>();
            while (rs.next()) {
                carModels.add(mapRowToCarModel(rs));
            }
            return carModels;
        }
    }

    public Optional<CarModel> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM car_model WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToCarModel(rs));
            } else {
                return Optional.empty();
            }
        }
    }

    public void save(CarModel carModel) throws SQLException {
        String sql = "INSERT INTO car_model (brand, model, country_origin, country_code) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, carModel.getBrand());
            ps.setString(2, carModel.getModel());
            ps.setString(3, carModel.getCountryOrigin());
            ps.setString(4, carModel.getCountryCode());
            ps.executeUpdate();
        }
    }

    public void update(CarModel carModel) throws SQLException {
        String sql = "UPDATE car_model SET brand = ?, model = ?, country_origin = ?, country_code = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, carModel.getBrand());
            ps.setString(2, carModel.getModel());
            ps.setString(3, carModel.getCountryOrigin());
            ps.setString(4, carModel.getCountryCode());
            ps.setLong(5, carModel.getId());
            ps.executeUpdate();
        }
    }

    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM car_model WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private CarModel mapRowToCarModel(ResultSet rs) throws SQLException {
        return CarModel.builder()
                .id(rs.getLong("id"))
                .brand(rs.getString("brand"))
                .model(rs.getString("model"))
                .countryOrigin(rs.getString("country_origin"))
                .countryCode(rs.getString("country_code"))
                .build();
    }
}
