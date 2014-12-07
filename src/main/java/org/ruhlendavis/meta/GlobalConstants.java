package org.ruhlendavis.meta;

public interface GlobalConstants {
    long REFERENCE_UNKNOWN = -1L;
    long REFERENCE_AMBIGUOUS = -2L;
    long REFERENCE_HOME = -3L;

    String DEFAULT_CONFIGURATION_PATH = "configuration.properties";
    String OPTION_NAME_DATABASE_PASSWORD = "database-password";
    String OPTION_NAME_DATABASE_PATH = "database-path";
    String OPTION_NAME_DATABASE_USERNAME = "database-username";

    String OPTION_NAME_TELNET_PORT = "telnet-port";
    String DEFAULT_TELNET_PORT_OLD = "9999";
}
