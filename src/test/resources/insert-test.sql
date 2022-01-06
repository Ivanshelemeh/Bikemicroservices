drop  table  bike_customer;
create table  bike_customer(
  id integer unsigned not null  auto_increment primary key,
    nickname varchar (100) not null unique ,
    mail varchar (20),
    password varchar (10) not null
    );