CREATE TABLE bike_order_item IF NOT EXISTS
(
    id integer unsigned not null auto_increment primary key,
    price_order decimal(0,2) default 0,
    order_type ENUM('bike','engine','detail'),
    premium_order boolean,
    bike_order_id int NOT NULL,
    CONSTRAINT fk_bike_items_id FOREIGN KEY(bike_order_id) REFERENCES bike_order(id)
    );

ALTER TABLE bike_order_item ADD COLUMN order_info LONGTEXT;
ALTER TABLE bike_order_item ADD COLUMN order_quantity INTEGER DEFAULT 0;
