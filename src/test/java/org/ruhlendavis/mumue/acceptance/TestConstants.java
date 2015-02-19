package org.ruhlendavis.mumue.acceptance;

import org.ruhlendavis.mumue.configuration.ConfigurationDefaults;

public interface TestConstants {
    String MEMORY_DATABASE = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    String QUERY_PURGE_DATABASE = "DROP ALL OBJECTS";
    String TEST_CONFIGURATION_FILE_PATH = "org/ruhlendavis/mumue/configuration/startup/" + ConfigurationDefaults.CONFIGURATION_PATH;
}
