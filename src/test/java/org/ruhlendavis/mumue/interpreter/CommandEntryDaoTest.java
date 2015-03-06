package org.ruhlendavis.mumue.interpreter;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.Collection;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import org.ruhlendavis.mumue.acceptance.DatabaseHelper;

public class CommandEntryDaoTest {
    private final QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithSchema();
    private CommandEntryDao dao = new CommandEntryDao();

    @Test
    public void getCommandsNeverReturnsNull() {
        assertNotNull(dao.getCommands());
    }

    @Test
    public void getCommandsReturnsOneCommand() {
        String display = RandomStringUtils.randomAlphabetic(17);
        insertCommand(display);

        Collection<CommandEntry> commands = dao.getCommands();

        assertThat(commands, is(not(empty())));

        CommandEntry commandEntry = commands.stream().findFirst().get();
        assertThat(commandEntry.getMinimumPartial(), equalTo(display));
    }

    @Test
    public void getCommandsReturnsThreeCommands() {
        insertCommand(RandomStringUtils.randomAlphabetic(17));
        insertCommand(RandomStringUtils.randomAlphabetic(16));
        insertCommand(RandomStringUtils.randomAlphabetic(15));

        Collection<CommandEntry> commands = dao.getCommands();

        assertThat(commands.size(), equalTo(3));
    }

    private void insertCommand(String display) {
        String sql = "insert into commands (display, minimumPartial, commandIdentifier, isToken)"
                + " values ('" + display + "', '" + display + "', 'say', false)";
        try {
            queryRunner.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
