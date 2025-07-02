ALTER TABLE bike_order
DROP COLUMN IF EXISTS order_type;
ALTER TABLE bike_order
DROP COLUMN IF EXISTS price;
ALTER TABLE bike_order
DROP COLUMN IF EXISTS premium_order;
ALTER TABLE bike_order DROP COLUMN IF EXISTS  order_info;
ALTER TABLE bike_order DROP COLUMN IF EXISTS order_quantity;

