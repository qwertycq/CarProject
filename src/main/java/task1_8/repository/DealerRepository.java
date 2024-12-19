package task1_8.repository;

import task1_8.entity.CarEntity;
import task1_8.entity.CarModelEntity;
import task1_8.entity.DealerEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DealerRepository {

    private final Connection connection;

    public DealerRepository(Connection connection) {
        this.connection = connection;
    }

    public List<DealerEntity> findAll() throws SQLException {
        String query = "SELECT d.*, c.*, cm.* FROM dealer d " +
                "LEFT JOIN car c ON d.id = c.dealer_id " +
                "LEFT JOIN car_model cm ON c.car_model_id = cm.id";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet result = stmt.executeQuery()) {

            List<DealerEntity> dealerEntityList = new ArrayList<>();
            DealerEntity activeDealerEntity = null;
            List<CarEntity> associatedCarEntities = null;

            while (result.next()) {
                long dealerId = result.getLong("d.id");

                if (activeDealerEntity == null || activeDealerEntity.getId() != dealerId) {
                    if (activeDealerEntity != null) {
                        activeDealerEntity.setCarEntities(associatedCarEntities);
                        dealerEntityList.add(activeDealerEntity);
                    }

                    activeDealerEntity = DealerEntity.builder()
                            .id(dealerId)
                            .name(result.getString("d.name"))
                            .build();
                    associatedCarEntities = new ArrayList<>();
                }

                if (result.getLong("c.id") != 0) {
                    CarModelEntity modelDetails = CarModelEntity.builder()
                            .id(result.getLong("cm.id"))
                            .brand(result.getString("cm.brand"))
                            .model(result.getString("cm.model"))
                            .countryOrigin(result.getString("cm.country_origin"))
                            .countryCode(result.getString("cm.country_code"))
                            .build();

                    CarEntity carEntityDetails = CarEntity.builder()
                            .id(result.getLong("c.id"))
                            .carModelEntity(modelDetails)
                            .state(result.getString("c.state"))
                            .equipment(result.getString("c.equipment"))
                            .color(result.getString("c.color"))
                            .price(result.getDouble("c.price"))
                            .build();

                    associatedCarEntities.add(carEntityDetails);
                }
            }

            if (activeDealerEntity != null) {
                activeDealerEntity.setCarEntities(associatedCarEntities);
                dealerEntityList.add(activeDealerEntity);
            }

            return dealerEntityList;
        }
    }

    public DealerEntity findById(Long id) throws SQLException {
        String query = "SELECT d.*, c.*, cm.* FROM dealer d " +
                "LEFT JOIN car c ON d.id = c.dealer_id " +
                "LEFT JOIN car_model cm ON c.car_model_id = cm.id " +
                "WHERE d.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet result = stmt.executeQuery()) {
                DealerEntity singleDealerEntity = null;
                List<CarEntity> carsLinked = new ArrayList<>();
                while (result.next()) {
                    if (singleDealerEntity == null) {
                        singleDealerEntity = DealerEntity.builder()
                                .id(result.getLong("d.id"))
                                .name(result.getString("d.name"))
                                .build();
                    }

                    if (result.getLong("c.id") != 0) {
                        CarModelEntity modelData = CarModelEntity.builder()
                                .id(result.getLong("cm.id"))
                                .brand(result.getString("cm.brand"))
                                .model(result.getString("cm.model"))
                                .countryOrigin(result.getString("cm.country_origin"))
                                .countryCode(result.getString("cm.country_code"))
                                .build();

                        CarEntity carEntityData = CarEntity.builder()
                                .id(result.getLong("c.id"))
                                .carModelEntity(modelData)
                                .state(result.getString("c.state"))
                                .equipment(result.getString("c.equipment"))
                                .color(result.getString("c.color"))
                                .price(result.getDouble("c.price"))
                                .build();

                        carsLinked.add(carEntityData);
                    }
                }
                if (singleDealerEntity != null) {
                    singleDealerEntity.setCarEntities(carsLinked);
                }
                return singleDealerEntity;
            }
        }
    }

    public void save(DealerEntity dealerEntity) throws SQLException {
        String query = "INSERT INTO dealer (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, dealerEntity.getName());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    dealerEntity.setId(keys.getLong(1));
                }
            }
        }
    }

    public void update(DealerEntity dealerEntity) throws SQLException {
        String query = "UPDATE dealer SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, dealerEntity.getName());
            stmt.setLong(2, dealerEntity.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteById(Long id) throws SQLException {
        String query = "DELETE FROM dealer WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
