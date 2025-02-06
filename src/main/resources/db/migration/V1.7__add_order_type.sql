ALTER TABLE bike_order ADD order_type ENUM('bike','engine','detail');
ALTER TABLE bike_order ADD INDEX idx_order_type_price(order_type,price);