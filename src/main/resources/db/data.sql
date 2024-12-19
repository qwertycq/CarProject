INSERT INTO car_model (brand, model, country_origin, country_code) VALUES
                           ('Toyota', 'Camry', 'Japan', 'JP'),
                           ('Ford', 'Focus', 'USA', 'US'),
                           ('BMW', 'X5', 'Germany', 'DE'),
                           ('Honda', 'Civic', 'Japan', 'JP');

INSERT INTO dealerEntity (name) VALUES
                              ('Central Dealer'),
                              ('West Side Dealer'),
                              ('East Side Dealer');

INSERT INTO carEntity (car_model_id, dealer_id, state, equipment, color, price) VALUES
                              (1, 1, 'NEW', 'Full Package', 'White', 30000.0),
                              (2, 2, 'USED', 'Basic', 'Blue', 15000.0),
                              (3, 1, 'NEW', 'Premium', 'Black', 70000.0),
                              (4, 3, 'USED', 'Standard', 'Red', 12000.0);
