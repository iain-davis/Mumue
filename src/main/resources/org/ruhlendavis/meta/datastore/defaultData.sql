insert into configuration_options (id, name, value) values ('database schema version', '0.0');

insert into universes (id, name, type) values (0, 'Player universe', 'player');
insert into players (name, password) values ('First', 'firstword');

insert into components (id, universe_id, reference, name, created, last_used, modified, use_count, location_id)
                values (0, 0, 'Waiting Room', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0, -1);
                create table spaces(id int primary key auto_increment, component_id int, drop_to_id int, owner_id int);

insert into spaces (id, component_id, drop_to_id, owner_id) values (0, 0, -1, -1);