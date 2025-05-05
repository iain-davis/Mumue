package org.mumue.mumue.interpreter;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

class CommandEntryDaoTest {
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final CommandEntryDao dao = new CommandEntryDao(database);

    @Test
    void getCommandsNeverReturnsNull() {
        Collection<CommandEntry> commands = dao.getCommands();
        assertThat(commands, notNullValue());
    }

    @Test
    void getCommandsReturnsOneCommand() {
        String display = RandomStringUtils.insecure().nextAlphabetic(17);
        insertCommand(display);

        Collection<CommandEntry> commands = dao.getCommands();

        assertThat(commands, is(not(empty())));

        @SuppressWarnings("OptionalGetWithoutIsPresent") CommandEntry commandEntry = commands.stream().findFirst().get();
        assertThat(commandEntry.getMinimumPartial(), equalTo(display));
        assertThat(commandEntry.getId(), greaterThanOrEqualTo(1L));
    }

    @Test
    void getCommandsReturnsThreeCommands() {
        insertCommand(RandomStringUtils.insecure().nextAlphabetic(17));
        insertCommand(RandomStringUtils.insecure().nextAlphabetic(16));
        insertCommand(RandomStringUtils.insecure().nextAlphabetic(15));

        Collection<CommandEntry> commands = dao.getCommands();

        assertThat(commands.size(), equalTo(3));
    }

    private void insertCommand(String display) {
        String sql = "insert into commands (display, minimumPartial, commandIdentifier, token)"
                + " values ('" + display + "', '" + display + "', 'say', false)";
        database.update(sql);
    }
}
