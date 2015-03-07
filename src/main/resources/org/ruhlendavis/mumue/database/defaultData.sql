insert into configuration_options (id, name, value) values (0, 'database schema version', '0.0');
insert into configuration_options (id, name, value) values (1, 'server locale', 'en-US');
insert into configuration_options (id, name, value) values (2, 'last component id', '0');

insert into commands (id, display, minimumPartial, commandIdentifier, token)
              values (0, 'say', 's', 'say', false);
insert into commands (id, display, minimumPartial, commandIdentifier, token)
              values (1, 'say', '"', 'say', true);
insert into commands (id, display, minimumPartial, commandIdentifier, token)
              values (2, 'pose', 'po', 'pose', false);
insert into commands (id, display, minimumPartial, commandIdentifier, token)
              values (3, 'pose', ':', 'pose', true);
insert into commands (id, display, minimumPartial, commandIdentifier, token)
              values (4, 'pose', ';', 'pose', true);
insert into commands (id, display, minimumPartial, commandIdentifier, token)
              values (5, 'directed say', '`', 'directed say', true);

insert into text (id, locale, name, text) values ( 0, 'en-US', 'welcome screen', 'Welcome to Mumue!\r\n');
insert into text (id, locale, name, text) values ( 1, 'en-US', 'login prompt', '\r\nEnter your login ID: ');
insert into text (id, locale, name, text) values ( 2, 'en-US', 'password prompt', 'Enter your password: ');
insert into text (id, locale, name, text) values ( 3, 'en-US', 'login failed', '\r\nLogin failed. Please try again.\r\n');
insert into text (id, locale, name, text) values ( 4, 'en-US', 'login success', '\r\nLogin succeeded. Welcome back!\r\n');
insert into text (id, locale, name, text) values ( 5, 'en-US', 'administrator main menu', 'A) Administer\r\n');
insert into text (id, locale, name, text) values ( 6, 'en-US', 'player main menu', 'C) Create a character\r\nP) Play a character\r\nQ) Quit\r\n');
insert into text (id, locale, name, text) values ( 7, 'en-US', 'invalid option', 'Invalid option selected. Try again:\r\n');
insert into text (id, locale, name, text) values ( 8, 'en-US', 'character name prompt', 'Enter a name for your character: ');
insert into text (id, locale, name, text) values ( 9, 'en-US', 'universe selection prompt', 'Select a universe for your character: ');
insert into text (id, locale, name, text) values (10, 'en-US', 'character name already exists', 'Character name already exists in that universe.\r\n');
insert into text (id, locale, name, text) values (11, 'en-US', 'character name taken by other player', 'Character name taken by another player.\r\n');
insert into text (id, locale, name, text) values (12, 'en-US', 'character selection prompt', 'Select a character to play: ');
insert into text (id, locale, name, text) values (13, 'en-US', 'character needed', 'You will need to create a character first. ');
insert into text (id, locale, name, text) values (14, 'en-US', 'enter universe', 'Entering the universe named ${universe name}...\r\n');

insert into players (id, loginId, password, locale, created, lastUsed, lastModified, useCount, administrator)
            values  (0, 'first', 'first password', 'en-US',
                     timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0,
                     true
            );

insert into universes (id, name, description, created, lastUsed, lastModified, useCount, startingSpaceId)
            values    (0, 'First Universe', 'First Universe',
                       timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0,
                       0
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
