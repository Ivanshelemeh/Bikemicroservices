create table if not exists bike_customer
(
    id
    integer
    unsigned
    not
    null
    auto_increment
    primary
    key,
    nickname
    varchar
(
    100
) not null unique ,
    mail varchar
(
    20
),
    password varchar
(
    1000
) not null
    );
create table if not exists bike_order
(
    id
    integer
    unsigned
    not
    null
    auto_increment
    primary
    key,
    product_name
    varchar
(
    100
) not null unique ,
    price double
(
    10,
    0
) not null,
    constraint fk_order foreign key
(
    id
) references bike_customer
(
    id
)
    );