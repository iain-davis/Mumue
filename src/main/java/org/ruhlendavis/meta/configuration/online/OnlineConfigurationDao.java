package org.ruhlendavis.meta.configuration.online;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

public class OnlineConfigurationDao {
    private static final String CONFIGURATION_OPTION_QUERY = "select value from configuration_options where name = ?";
    private static final String CONFIGURATION_OPTION_VALUE_COLUMN = "value";
    private final QueryRunner queryRunner;

    public OnlineConfigurationDao(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    public String getConfigurationOption(String optionName) {
        ResultSetHandler resultSetHandler = new ScalarHandler<>(CONFIGURATION_OPTION_VALUE_COLUMN);
        try {
            return StringUtils.defaultString((String) queryRunner.query(CONFIGURATION_OPTION_QUERY, resultSetHandler, optionName));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
