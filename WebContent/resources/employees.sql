 CREATE TABLE employees(
	email varchar(50),
	password varchar(20) NOT NULL,
	fullname varchar(100) DEFAULT '',
	PRIMARY KEY (email)
);