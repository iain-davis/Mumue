create table configuration_options(id int primary key auto_increment, name varchar(255), value varchar(255));
create table text(id int primary key auto_increment, locale varchar(15), name varchar(255), text varchar(255));
create table commands(id int primary key auto_increment, display varchar(255), minimum_partial varchar(255),
                      command_class varchar(255), is_token tinyint, available_unauthenticated tinyint);

create table players(loginId varchar(255) primary key, password varchar(255), locale varchar(15),
                     created timestamp, lastUsed timestamp, lastModified timestamp, useCount long,
                     administrator boolean
                    );

create table universes(id int primary key auto_increment, name varchar(255), description varchar(255),
                       created timestamp, lastUsed timestamp, lastModified timestamp, useCount long
                      );

create table spaces(id int primary key auto_increment, name varchar(255), description varchar(255),
                    created timestamp, lastUsed timestamp, lastModified timestamp, useCount long,
                    universeId int, locationId int,
                    drop_to_id int, owner_id int
                    );

