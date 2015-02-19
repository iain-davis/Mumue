create table configuration_options(id int primary key auto_increment, name varchar(255), value varchar(255));
create table text(id int primary key auto_increment, locale varchar(15), name varchar(255), text varchar(255));
create table commands(id int primary key auto_increment, display varchar(255), minimum_partial varchar(255),
                      command_class varchar(255), is_token tinyint, available_unauthenticated tinyint);
create table universes(id int primary key auto_increment, name varchar(255), type varchar(255));
create table players(id int primary key auto_increment, loginId varchar(255), name varchar(255), password varchar(255),
                     locationId int);
create table components(id int primary key auto_increment, name varchar(255),
                        description varchar(255), created timestamp, last_used timestamp, modified timestamp,
                        use_count long, universe_id int, location_id int);
create table spaces(id int primary key auto_increment, component_id int, drop_to_id int, owner_id int);
