package org.mumue.mumue.database;

import jakarta.inject.Inject;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.mumue.mumue.configuration.online.OnlineConfigurationOptionName;

public class DatabaseInitializerDao {
    static final String DEFAULT_DATA_SCRIPT = "RUNSCRIPT FROM 'classpath:org/mumue/mumue/database/defaultData.sql'";
    static final String SCHEMA_SCRIPT = "RUNSCRIPT FROM 'classpath:org/mumue/mumue/database/schema.sql'";
    private static final String CHECK_CONFIGURATION_TABLE_VERSION = "select value from configuration_options where name = '" + OnlineConfigurationOptionName.SERVER_VERSION + "'";
    private static final String CHECK_CONFIGURATION_TABLE_EXISTENCE = "select count(*) from information_schema.tables where table_name = 'CONFIGURATION_OPTIONS'";
    private final DatabaseAccessor database;

    @Inject
    public DatabaseInitializerDao(DatabaseAccessor database) {
        this.database = database;
    }

    public boolean hasSchema() {
        ResultSetHandler<Long> resultSetHandler = new ScalarHandler<>(1);
        long found = database.query(CHECK_CONFIGURATION_TABLE_EXISTENCE, resultSetHandler);
        return found != 0;
    }

    public boolean hasData() {
        ResultSetHandler<String> resultSetHandler = new ScalarHandler<>(1);
        String version = database.query(CHECK_CONFIGURATION_TABLE_VERSION, resultSetHandler);
        return StringUtils.isNotBlank(version);
    }

    public void loadSchema() {
        database.update(SCHEMA_SCRIPT);
    }

    public void loadDefaultData() {
        database.update(DEFAULT_DATA_SCRIPT);
    }
}
