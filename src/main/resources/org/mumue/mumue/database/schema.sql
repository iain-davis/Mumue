create table configuration_ports(id int primary key auto_increment, port int unique, type varchar(25), supportsMenus boolean);
create table configuration_options(id int primary key auto_increment, name varchar(255), value varchar(255));
create table text(id int primary key auto_increment, locale varchar(15), name varchar(255), text varchar(255));
create table commands(id int primary key auto_increment, display varchar(255), minimumPartial varchar(255), commandIdentifier varchar(255), token boolean);

create table players(id int primary key auto_increment, loginId varchar(255) unique, password varchar(255), locale varchar(15),
                     created timestamp, lastUsed timestamp, lastModified timestamp, useCount long,
                     administrator boolean
                    );

create table universes(id int primary key, name varchar(255), description varchar(255),
                       created timestamp, lastUsed timestamp, lastModified timestamp, useCount long,
                       startingSpaceId int
                      );

create table characters(id int primary key, name varchar(255), description varchar(255),
                       created timestamp, lastUsed timestamp, lastModified timestamp, useCount long,
                       locationId int, universeId int,
                       playerId int
                      );

create table spaces(id int primary key, name varchar(255), description varchar(255),
                    created timestamp, lastUsed timestamp, lastModified timestamp, useCount long,
                    universeId int, locationId int,
                    drop_to_id int, owner_id int
                    );
