package org.ruhlendavis.meta.acceptance;

import org.ruhlendavis.meta.configuration.ConfigurationDefaults;

public interface TestConstants {
    String MEMORY_DATABASE = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    String QUERY_PURGE_DATABASE = "DROP ALL OBJECTS";
    String TEST_CONFIGURATION_FILE_PATH = "org/ruhlendavis/meta/configuration/startup/" + ConfigurationDefaults.CONFIGURATION_PATH;
    String ACCEPTANCE_STARTUP_CONFIGURATION_PATH = "org/ruhlendavis/meta/acceptance/acceptance-startup-configuration.properties";
    String ACCEPTANCE_DATABASE_PATH = "./acceptance.h2.db";
}
