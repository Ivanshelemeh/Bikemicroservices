create  table if not exists bike_customer(
    id integer unsigned not null  auto_increment primary key
    references bike_order(id),
    nickname varchar (100) not null unique ,
    mail varchar (20),
    password varchar (10) not null
);
create table if not exists bike_order(
    id integer  unsigned not null auto_increment primary key
    references bike_customer(id),
    product_name varchar (100) not null  unique ,
    price double (10,0) not null,
    bike_order_list varchar (100)
);