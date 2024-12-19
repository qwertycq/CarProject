CREATE TABLE IF NOT EXISTS car_model (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           brand VARCHAR(255) NOT NULL,
                           model VARCHAR(255) NOT NULL,
                           country_origin VARCHAR(255) NOT NULL,
                           country_code VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS dealerEntity (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS carEntity (
                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     car_model_id BIGINT,
                     dealer_id BIGINT,
                     state VARCHAR(255),
                     equipment VARCHAR(255),
                     color VARCHAR(255),
                     price DOUBLE,
                     FOREIGN KEY (car_model_id) REFERENCES car_model(id),
                     FOREIGN KEY (dealer_id) REFERENCES dealerEntity(id)
);