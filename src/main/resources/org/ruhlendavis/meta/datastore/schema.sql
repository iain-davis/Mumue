create table configuration_options(id int primary key auto_increment, name varchar(255), value varchar(255));
create table components(id int primary key auto_increment, reference long unique, name varchar(255), created timestamp, last_used timestamp, modified timestamp, use_count long, location_id int);
create table players(id int primary key auto_increment, name varchar(255), password varchar(255));
