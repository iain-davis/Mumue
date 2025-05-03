package org.mumue.mumue.interpreter;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;

public class CommandEntryDaoTest {
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private CommandEntryDao dao = new CommandEntryDao(database);

    @Test
    public void getCommandsNeverReturnsNull() {
        assertNotNull(dao.getCommands());
    }

    @Test
    public void getCommandsReturnsOneCommand() {
        String display = RandomStringUtils.insecure().nextAlphabetic(17);
        insertCommand(display);

        Collection<CommandEntry> commands = dao.getCommands();

        assertThat(commands, is(not(empty())));

        CommandEntry commandEntry = commands.stream().findFirst().get();
        assertThat(commandEntry.getMinimumPartial(), equalTo(display));
        assertThat(commandEntry.getId(), greaterThanOrEqualTo(1L));
    }

    @Test
    public void getCommandsReturnsThreeCommands() {
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
