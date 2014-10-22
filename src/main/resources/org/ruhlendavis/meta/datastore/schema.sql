create table configuration_options(id int primary key auto_increment, name varchar(255), value varchar(255));
create table universes(id int primary key auto_increment, name varchar(255), type varchar(255));
create table players(id int primary key auto_increment, name varchar(255), password varchar(255));
create table components(id int primary key auto_increment, universe_id int, reference long unique, name varchar(255),
                        description varchar(255), created timestamp, last_used timestamp, modified timestamp, use_count long, location_id int);
create table spaces(id int primary key auto_increment, component_id int, drop_to_id int, owner_id int);
