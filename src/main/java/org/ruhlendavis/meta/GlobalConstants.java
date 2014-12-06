package org.ruhlendavis.meta;

public interface GlobalConstants {
    long REFERENCE_UNKNOWN = -1L;
    long REFERENCE_AMBIGUOUS = -2L;
    long REFERENCE_HOME = -3L;

    String DEFAULT_CONFIGURATION_PATH = "configuration.properties";
    String DEFAULT_DATABASE_PASSWORD = "metadatabase";
    String DEFAULT_DATABASE_PATH = "./metadatabase";
    String DEFAULT_DATABASE_USERNAME = "metadatabase";

    String DEFAULT_TELNET_PORT_OLD = "9999";
    String OPTION_NAME_DATABASE_PASSWORD = "database-password";
    String OPTION_NAME_DATABASE_PATH = "database-path";
    String OPTION_NAME_DATABASE_USERNAME = "database-username";
    String OPTION_NAME_TELNET_PORT = "telnet-port";
    int DEFAULT_TELNET_PORT = 9999;
}
