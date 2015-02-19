package org.ruhlendavis.mumue.database;

import org.ruhlendavis.mumue.configuration.online.OnlineConfigurationOptionName;

public interface SqlConstants {
    String DRIVER_CLASS_NAME = "org.h2.Driver";

    String CHECK_CONFIGURATION_TABLE_EXISTENCE = "select count(*) from information_schema.tables where table_name = 'CONFIGURATION_OPTIONS'";
    String CHECK_CONFIGURATION_TABLE_VERSION = "select value from configuration_options where name = '" + OnlineConfigurationOptionName.SERVER_VERSION + "'";

    String SCHEMA_SCRIPT = "RUNSCRIPT FROM 'classpath:org/ruhlendavis/mumue/database/schema.sql'";
    String DEFAULT_DATA_SCRIPT = "RUNSCRIPT FROM 'classpath:org/ruhlendavis/mumue/database/defaultData.sql'";
}