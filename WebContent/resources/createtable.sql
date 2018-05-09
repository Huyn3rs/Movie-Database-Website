CREATE TABLE movies(
	id int NOT NULL AUTO_INCREMENT,
	title varchar(100) NOT NULL,
	year int NOT NULL,
	director varchar(100) NOT NULL DEFAULT '',
	banner_url varchar(200) DEFAULT '',
	trailer_url varchar(200) DEFAULT '',
	PRIMARY KEY (id),
	FULLTEXT(title)
) ENGINE=InnoDB;

CREATE TABLE stars(
	id int NOT NULL AUTO_INCREMENT,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	dob date,
	photo_url varchar(200) DEFAULT '',
	PRIMARY KEY (id)
)ENGINE=InnoDB;

CREATE TABLE stars_in_movies(
	star_id int NOT NULL,
	movie_id int NOT NULL,
	FOREIGN KEY (star_id) 
		REFERENCES stars(id) 
		ON UPDATE CASCADE 
		ON DELETE CASCADE,
	FOREIGN KEY (movie_id) 
		REFERENCES movies(id) 
		ON UPDATE CASCADE 
		ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE genres(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(32) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE genres_in_movies(
	genre_id int NOT NULL,
	movie_id int NOT NULL,
	FOREIGN KEY (genre_id) 
		REFERENCES genres(id) 
		ON UPDATE CASCADE 
		ON DELETE CASCADE,
	FOREIGN KEY (movie_id) 
		REFERENCES movies(id) 
		ON UPDATE CASCADE 
		ON DELETE CASCADE
);

CREATE TABLE creditcards(
	id varchar(20) NOT NULL DEFAULT '',
	first_name varchar(50) DEFAULT '',
	last_name varchar(50) DEFAULT '',
	expiration date NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE customers(
	id int NOT NULL AUTO_INCREMENT,
	first_name varchar(50) NOT NULL DEFAULT '',
	last_name varchar(50) NOT NULL DEFAULT '',
	cc_id varchar(20) NOT NULL DEFAULT '',
	address varchar(200) NOT NULL DEFAULT '',
	email varchar(50) NOT NULL DEFAULT '',
	password varchar(20) NOT NULL DEFAULT '',
	PRIMARY KEY (id),
	FOREIGN KEY(cc_id) 
		REFERENCES creditcards(id) 
		ON UPDATE CASCADE 
		ON DELETE CASCADE
);

CREATE TABLE sales(
	id int NOT NULL AUTO_INCREMENT,
	customer_id int NOT NULL,
	movie_id int NOT NULL,
	sale_date date,
	PRIMARY KEY(id),
	FOREIGN KEY(customer_id) 
		REFERENCES customers(id) 
		ON UPDATE CASCADE 
		ON DELETE CASCADE,
	FOREIGN KEY(movie_id) 
		REFERENCES movies(id) 
		ON UPDATE CASCADE 
		ON DELETE CASCADE
);

