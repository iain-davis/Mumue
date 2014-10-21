package org.ruhlendavis.meta;

public interface GlobalConstants {
    public static final long REFERENCE_UNKNOWN = -1L;
    public static final long REFERENCE_AMBIGUOUS = -2L;
    public static final long REFERENCE_HOME = -3L;

    public static final String DEFAULT_CONFIGURATION_PATH = "configuration.properties";
    public static final String DEFAULT_DATABASE_PASSWORD = "metadatabase";
    public static final String DEFAULT_DATABASE_PATH = "./metadatabase";
    public static final String DEFAULT_DATABASE_USERNAME = "metadatabase";

    public static final String DEFAULT_TELNET_PORT = "9999";
    public static final String OPTION_NAME_DATABASE_PASSWORD = "database password";
    public static final String OPTION_NAME_DATABASE_PATH = "database path";
    public static final String OPTION_NAME_DATABASE_USERNAME = "database username";
    public static final String OPTION_NAME_TELNET_PORT = "telnet port";
}
