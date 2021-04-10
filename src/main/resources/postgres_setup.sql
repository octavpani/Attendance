CREATE USER user WITH PASSWORD 'pass';
CREATE DATABASE attendance_db;

CREATE TABLE attendance (
id serial,
username varchar(20) NOT NULL,
year integer NOT NULL,
month integer NOT NULL,
day integer NOT NULL,
sta_hour integer NOT NULL,
sta_min  integer NOT NULL,
end_hour integer NOT NULL,
end_min  integer NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE siteuser (
id serial,
username varchar(255) NOT NULL,
password varchar(255) NOT NULL,
role varchar(20) NOT NULL,
avatar text NOT NULL,
PRIMARY KEY (id)
) ;

insert into siteuser values (1, 'admin', '$2a$10$3nKgqsnBKvwLvx3bEsSGqOEPsTE55pDhZ/mZoLd2YNUvmHHcDRsMG', 'ADMIN', '');
insert into siteuser values (2, 'user', '$2a$10$z1WsixL0fN/iw4fCvU3OfuKQuRZhZXCGzkFqJ57zTvkXJZKoDHjnS', 'USER', '');
insert into siteuser values (3, 'snoopy', '$2a$10$VRDmtzk2yP4fjqIW01P7xuzDl6Ftqm/cKLnnuvaW4/d4IDBMPu/ZW', 'USER', '');


insert into attendance values (1, 'user', 2021, 4, 1, 7, 0, 18, 30);
insert into attendance values (2, 'user', 2022, 4, 8, 7, 5, 19, 30);
insert into attendance values (3, 'user', 2025, 5, 2, 8, 12, 22, 30);
insert into attendance values (4, 'user', 2027, 9, 23, 9, 12, 20, 30);
insert into attendance values (5, 'user', 2028, 11, 30, 7, 12, 20, 30);
insert into attendance values (6, 'user', 2021, 4, 1, 7, 0, 18, 30);
insert into attendance values (7, 'user', 2022, 4, 8, 7, 5, 19, 30);
insert into attendance values (8, 'user', 2025, 5, 2, 8, 12, 22, 30);
insert into attendance values (9, 'user', 2027, 9, 23, 9, 12, 20, 30);
insert into attendance values (10, 'user', 2028, 11, 30, 7, 12, 20, 30);
insert into attendance values (11, 'admin', 2021, 7, 21, 6, 0, 20, 30);
insert into attendance values (12, 'admin', 2021, 9, 5, 8, 18, 20, 0);
insert into attendance values (13, 'admin', 2023, 8, 18, 7, 0, 20, 30);
insert into attendance values (14, 'admin', 2024, 9, 15, 9, 18, 22, 0);
insert into attendance values (15, 'admin', 2022, 4, 1, 6, 0, 21, 30);
insert into attendance values (16, 'admin', 2021, 2, 11, 6, 0, 20, 30);
insert into attendance values (17, 'admin', 2021, 1, 5, 8, 18, 20, 0);
insert into attendance values (18, 'admin', 2023, 11, 18, 7, 0, 20, 30);
insert into attendance values (19, 'admin', 2024, 12, 14, 9, 18, 22, 0);
insert into attendance values (20, 'admin', 2022, 6, 2, 6, 0, 21, 30);

