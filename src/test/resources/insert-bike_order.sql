
create table bike_order
(
    id  integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_name varchar(100) not null,
    price double (10.0) not null,
    order_quantity integer,
    order_info varchar(200),
    created_at TIMESTAMP,
    lastmod_at TIMESTAMP,
    order_type varchar,
    premium_order boolean,
    version integer

);

create table bike_customer
(
    id               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nickname         varchar(100) not null unique,
    mail             varchar(20),
    password         varchar(100) not null,
    create_at        TIMESTAMP,
    lastmodify_at    TIMESTAMP,
    premium_customer boolean,
    versionId integer

);
