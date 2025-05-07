package org.mumue.mumue.configuration.online;

import jakarta.inject.Inject;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.mumue.mumue.database.DatabaseAccessor;

class OnlineConfigurationDao {
    private static final String CONFIGURATION_OPTION_SETTING_COLUMN = "setting";
    private static final String CONFIGURATION_OPTION_QUERY = "select " + CONFIGURATION_OPTION_SETTING_COLUMN + " from configuration_options where name = ?";
    private final DatabaseAccessor database;

    @Inject
    public OnlineConfigurationDao(DatabaseAccessor database) {
        this.database = database;
    }

    public String getConfigurationOption(String optionName) {
        ResultSetHandler<String> resultSetHandler = new ScalarHandler<>(CONFIGURATION_OPTION_SETTING_COLUMN);
        return StringUtils.defaultIfBlank(database.query(CONFIGURATION_OPTION_QUERY, resultSetHandler, optionName), "");
    }
}
