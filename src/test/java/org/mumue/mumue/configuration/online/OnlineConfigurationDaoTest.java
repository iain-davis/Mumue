package org.mumue.mumue.configuration.online;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;

class OnlineConfigurationDaoTest {
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final OnlineConfigurationDao dao = new OnlineConfigurationDao(database);

    @Test
    void getConfigurationOption() {
        String option = RandomStringUtils.insecure().nextAlphabetic(13);
        String value = RandomStringUtils.insecure().nextAlphabetic(14);

        insertOption(option, value);

        assertThat(dao.getConfigurationOption(option), equalTo(value));
    }

    @Test
    void returnEmptyStringWhenEmpty() {
        String option = RandomStringUtils.insecure().nextAlphabetic(13);
        insertOption(option, "");

        assertThat(dao.getConfigurationOption(option), equalTo(""));
    }

    @Test
    void returnEmptyStringWhenBlank() {
        String option = RandomStringUtils.insecure().nextAlphabetic(13);
        insertOption(option, "   \t\r\n");

        assertThat(dao.getConfigurationOption(option), equalTo(""));
    }

    @Test
    void getConfigurationOptionWhenOptionNotPresent() {
        String option = RandomStringUtils.insecure().nextAlphabetic(13);
        assertThat(dao.getConfigurationOption(option), equalTo(""));
    }

    private void insertOption(String name, String value) {
        database.update("insert into configuration_options (name, setting) values (?, ?)", name, value);
    }
}
