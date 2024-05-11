CREATE TABLE price (
    vehicle_id INT PRIMARY KEY,
    price DECIMAL(19, 2),
    currency VARCHAR(10)
);

INSERT INTO price (vehicle_id, price, currency) VALUES (1, 10000.00, 'USD');
INSERT INTO price (vehicle_id, price, currency) VALUES (2, 20000.00, 'USD');
INSERT INTO price (vehicle_id, price, currency) VALUES (3, 30000.00, 'USD');