drop table if exists cart;
drop table if exists orders;
drop table if exists image;
drop table if exists usr;
drop table if exists book;

create table book(
	id int(10),
	name varchar(255),
	author varchar(255),
	isbn varchar(100),
	price decimal(10,2),
	quantity int(10),
	desp varchar(255),
	primary key (id)
);

create table usr(
	username varchar(255),
	password varchar(255),
	primary key (username)
);

create table image(
	id int(10),
	img blob,
	foreign key (id) references book(id) on delete cascade
);

create table orders(
	username varchar(255),
	id int(10),
	quantity int(10),
	foreign key (username) references usr(username) on delete cascade,
	foreign key (id) references book(id) on delete cascade
);

create table cart(
	username varchar(255),
	id int(10),
	quantity int(10),
	foreign key (username) references usr(username) on delete cascade,
	foreign key (id) references book(id) on delete cascade
);

insert into usr values ('admin','123');
insert into usr values ('test','123');
