package org.ruhlendavis.meta.configuration.online;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import org.ruhlendavis.meta.acceptance.DatabaseHelper;

public class OnlineConfigurationDaoTest {
    private OnlineConfigurationDao dao;
    private QueryRunner queryRunner;

    @Before
    public void beforeEach() throws SQLException {
        queryRunner = DatabaseHelper.setupTestDatabaseWithSchema();
        dao = new OnlineConfigurationDao(queryRunner);
    }

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
        try {
            queryRunner.update("insert into configuration_options (name, value) values (?, ?)", name, value);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
