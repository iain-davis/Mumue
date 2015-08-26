package org.mumue.mumue.configuration;

public interface ConfigurationDefaults {
    // Command line section
    String CONFIGURATION_PATH = "startup-configuration.properties";

    // Startup configuration section
    String DATABASE_PASSWORD = "mumuedatabase";
    String DATABASE_PATH = "./mumuedatabase";
    String DATABASE_USERNAME = "mumuedatabase";
    String DATABASE_URL = "jdbc:h2:" + ConfigurationDefaults.DATABASE_PATH + ";MV_STORE=FALSE;MVCC=FALSE";

    int TELNET_PORT = 9999;
    // Primary configuration section
    String SERVER_LOCALE = "en-US";
    long LAST_COMPONENT_ID = 0L;
}
