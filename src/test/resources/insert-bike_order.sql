drop table bike_order;
create table bike_order
(id integer unsigned not null auto_increment primary  key,
    product_name varchar(100) not null,
    price double (10.0) not null
    );