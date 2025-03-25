ALTER TABLE cus_transaction ADD COLUMN owner_transaction VARCHAR(100);
ALTER TABLE cus_transaction ADD COLUMN description_detail VARCHAR(200);

ALTER TABLE bike_order ADD COLUMN  order_info LONGTEXT;
ALTER TABLE bike_order ADD COLUMN  order_quantity INTEGER;

ALTER TABLE bike_order DROP COLUMN order_type;
ALTER TABLE bike_order ADD COLUMN order_type varchar(100) NOT NULL CHECK ( order_type IN ('DETAIL','ENGINE','BIKE'));