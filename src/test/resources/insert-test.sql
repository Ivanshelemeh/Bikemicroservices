drop table bike_customer;
create table bike_customer
(
    id               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nickname         varchar(100) not null unique,
    mail             varchar(20),
    password         varchar(100) not null,
    create_at        TIMESTAMP,
    lastmodify_at    TIMESTAMP,
    premium_customer boolean,
    version_id       integer

);

create table b_order
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_name varchar(100) not null,
    price double (10.0) not null,
    customer_id  integer,
    constraint fk_order_id foreign key (customer_id) references bike_customer (id)
);
drop table b_order;

create table cus_tran
(
    id
        BIGINT
        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    period_tran
        TIMESTAMP
        NOT
            NULL,
    cus_id
        int
        NOT
            NULL,
    CONSTRAINT
        fk_trans_cus
        FOREIGN
            KEY
            (
             cus_id
                ) REFERENCES bike_customer
            (
             id
                )
);
drop table cus_tran;