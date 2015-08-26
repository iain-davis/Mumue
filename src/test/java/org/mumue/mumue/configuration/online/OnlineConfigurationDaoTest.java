package org.mumue.mumue.configuration.online;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.acceptance.DatabaseHelper;
import org.mumue.mumue.database.DatabaseAccessor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class OnlineConfigurationDaoTest {
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final OnlineConfigurationDao dao = new OnlineConfigurationDao(database);

    @Test
    public void getConfigurationOption() {
        String option = RandomStringUtils.randomAlphabetic(13);
        String value = RandomStringUtils.randomAlphabetic(14);

        insertOption(option, value);

        assertThat(dao.getConfigurationOption(option), equalTo(value));
    }

    @Test
    public void getConfigurationOptionWhenOptionNotPresent() {
        String option = RandomStringUtils.randomAlphabetic(13);
        assertThat(dao.getConfigurationOption(option), equalTo(""));
    }

    private void insertOption(String name, String value) {
        database.update("insert into configuration_options (name, value) values (?, ?)", name, value);
    }
}
