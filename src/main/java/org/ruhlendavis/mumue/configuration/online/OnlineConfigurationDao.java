package org.ruhlendavis.mumue.configuration.online;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import org.ruhlendavis.mumue.database.DatabaseAccessor;
import org.ruhlendavis.mumue.database.DatabaseAccessorProvider;

class OnlineConfigurationDao {
    private static final String CONFIGURATION_OPTION_QUERY = "select value from configuration_options where name = ?";
    private static final String CONFIGURATION_OPTION_VALUE_COLUMN = "value";

    public String getConfigurationOption(String optionName) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler resultSetHandler = new ScalarHandler<>(CONFIGURATION_OPTION_VALUE_COLUMN);
        return StringUtils.defaultString((String) database.query(CONFIGURATION_OPTION_QUERY, resultSetHandler, optionName));
    }
}
