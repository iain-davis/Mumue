insert into configuration_options (id, name, value) values (0, 'database schema version', '0.0');
insert into configuration_options (id, name, value) values (1, 'server locale', 'en-US');

insert into text (id, locale, name, text) values (0, 'en-US', 'welcome screen', 'Welcome to Mumue!\r\n');
insert into text (id, locale, name, text) values (1, 'en-US', 'login prompt', '\r\nEnter your login ID: ');
insert into text (id, locale, name, text) values (2, 'en-US', 'password prompt', 'Enter your password: ');
insert into text (id, locale, name, text) values (3, 'en-US', 'login failed', '\r\nLogin failed. Please try again.\r\n');
insert into text (id, locale, name, text) values (4, 'en-US', 'login success', '\r\nLogin succeeded. Welcome back!\r\n');
insert into text (id, locale, name, text) values (5, 'en-US', 'administrator main menu', 'A) Administer\r\n');
insert into text (id, locale, name, text) values (6, 'en-US', 'player main menu', 'C) Create a character\r\nP) Play a character\r\nQ) Quit\r\n');
insert into text (id, locale, name, text) values (7, 'en-US', 'invalid option', 'Invalid option selected. Try again:\r\n');

insert into players (loginId, password, locale, created, lastUsed, lastModified, useCount, administrator)
            values  ('first', 'first password', 'en-US',
                     timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0,
                     true
            );

insert into universes (id, name, description, created, lastUsed, lastModified, useCount)
            values    (0, 'First Universe', 'First Universe',
                       timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0
                      );

insert into spaces (id, name, description,
                    created, lastUsed, lastModified, useCount,
                    universeId, locationId,
                    drop_to_id, owner_id)
            values (0, 'Waiting Room', 'White nothingness everywhere.',
                    timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0,
                     0, -1,
                    -1, -1
                   );
