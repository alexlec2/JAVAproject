CREATE SCHEMA `db_restaurant` ;
use `db_restaurant`;

CREATE TABLE `table`(
	id_table int not null primary key,
    location varchar(255),
    `is_occcupied` boolean,
    number_people int
);

CREATE TABLE `order`(
	id_order int auto_increment primary key, 
    id_table int, 
    id_user int, 
    status_order varchar(255),
    date_order datetime
);

create table `user`(
id_user int auto_increment primary key,
username varchar(50) not null,
password varchar(50) not null,
type varchar(50));

ALTER TABLE `order` ADD FOREIGN KEY (id_user) REFERENCES `user`(id_user);
ALTER TABLE `order` ADD FOREIGN KEY (id_table) REFERENCES `table`(id_table);
ALTER TABLE `order` ADD FOREIGN KEY (id_table) REFERENCES `table`(id_table);

create table dish(
id_dish int auto_increment primary key,
name varchar(50),
price varchar(50),
type varchar(50),
available int);


create table ordered(
id_order int,
id_dish int,
number int,
id_table int,
status_order varchar(50),
foreign key(id_order) references `order`(id_order),
foreign key(id_dish) references dish(id_dish),
foreign key(id_table) references `order`(id_table)
);

insert into `user`(username, password, type) values("root", "root", "Big Boss");
