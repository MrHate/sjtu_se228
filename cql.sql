drop table if exists book;

create table book(
	id int(10),
	name varchar(255),
	price decimal(10,2),
	quantity int(10),
	desp varchar(255),
	primary key (id)
);

insert into book values (0, 'Black book', 100,1,'This is a book in black');
insert into book values (1, 'God of Animal', 20,1,'Respect to animal');
insert into book values (2, 'Zen and Motor', 40,2,'Ride to the highway');
insert into book values (3, 'OOP Programming', 120,1,'Object oriented programming');
insert into book values (4, 'JavaScript', 60,1,'Learn to use JS');
insert into book values (5, 'Natural language processing', 80,1,'A way to use Java');
