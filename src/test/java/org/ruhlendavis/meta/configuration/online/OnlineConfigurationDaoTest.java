package org.ruhlendavis.meta.configuration.online;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import org.ruhlendavis.meta.configuration.TestConstants;
import org.ruhlendavis.meta.database.QueryRunnerFactory;
import org.ruhlendavis.meta.database.SqlConstants;

public class OnlineConfigurationDaoTest {
    private OnlineConfigurationDao dao;
    private QueryRunner queryRunner;

    @Before
    public void beforeEach() throws SQLException {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(SqlConstants.DRIVER_CLASS_NAME);
        source.setUsername("user");
        source.setPassword("password");
        source.setUrl(TestConstants.MEMORY_DATABASE);
        QueryRunnerFactory queryRunnerFactory = new QueryRunnerFactory();
        queryRunner = queryRunnerFactory.createQueryRunner(source);
        dao = new OnlineConfigurationDao(queryRunner);
        queryRunner.update(TestConstants.QUERY_PURGE_DATABASE);
        queryRunner.update(SqlConstants.SCHEMA_SCRIPT);
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
