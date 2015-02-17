insert into configuration_options (id, name, value) values (0, 'database schema version', '0.0');
insert into configuration_options (id, name, value) values (1, 'server locale', 'en-US');

insert into text (id, locale, name, text) values (0, 'en-US', 'welcome screen', 'Welcome to MetaMUCK!\r\n');
insert into text (id, locale, name, text) values (1, 'en-US', 'login prompt', '\r\nEnter your login ID: ');
insert into text (id, locale, name, text) values (2, 'en-US', 'password prompt', 'Enter your password: ');
insert into text (id, locale, name, text) values (3, 'en-US', 'login failed', '\r\nLogin failed. Please try again.\r\n');
insert into text (id, locale, name, text) values (4, 'en-US', 'login success', '\r\nLogin succeeded. Welcome back!\r\n');

insert into universes (id, name, type) values (0, 'Player universe', 'player');

insert into players (id, login, name, password) values (0, 'first', 'First Player', 'firstword', 0);

insert into components (id, universe_id, reference, name, description, created, last_used, modified, use_count, location_id)
                values (0, 0, 0, 'Waiting Room', 'White nothingness everywhere.', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0, -1);

insert into spaces (id, component_id, drop_to_id, owner_id) values (0, 0, -1, -1);
