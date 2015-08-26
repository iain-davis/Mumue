package org.mumue.mumue.configuration.online;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.mumue.mumue.database.DatabaseAccessor;

import javax.inject.Inject;

class OnlineConfigurationDao {
    private static final String CONFIGURATION_OPTION_QUERY = "select value from configuration_options where name = ?";
    private static final String CONFIGURATION_OPTION_VALUE_COLUMN = "value";
    private final DatabaseAccessor database;

    @Inject
    public OnlineConfigurationDao(DatabaseAccessor database) {
        this.database = database;
    }

    public String getConfigurationOption(String optionName) {
        ResultSetHandler resultSetHandler = new ScalarHandler<>(CONFIGURATION_OPTION_VALUE_COLUMN);
        return StringUtils.defaultString((String) database.query(CONFIGURATION_OPTION_QUERY, resultSetHandler, optionName));
    }
}
