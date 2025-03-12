create table cus_transaction if not exists(
    id BIGINT unsigned not null primary key,
    period_transaction TIMESTAMP NOT NULL,
    customer_id int NOT NULL,
    CONSTRAINT fk_trans_cus  FOREIGN KEY (customer_id) REFERENCES bike_customer(id)
);
