insert into configuration_options (id, name, value) values (0, 'database schema version', '0.0');

insert into universes (id, name, type) values (0, 'Player universe', 'player');
insert into players (id, name, password) values (0, 'First', 'firstword');

insert into components (id, universe_id, reference, name, description, created, last_used, modified, use_count, location_id)
                values (0, 0, 0, 'Waiting Room', 'White nothingness everywhere.', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0, -1);

insert into spaces (id, component_id, drop_to_id, owner_id) values (0, 0, -1, -1);
