package org.ruhlendavis.mumue.configuration;

public interface ConfigurationDefaults {
    // Command line section
    String CONFIGURATION_PATH = "startup-configuration.properties";

    // Startup configuration section
    String DATABASE_PASSWORD = "mumuedatabase";
    String DATABASE_PATH = "./mumuedatabase";
    String DATABASE_USERNAME = "mumuedatabase";
    int TELNET_PORT = 9999;

    // Primary configuration section
    String SERVER_LOCALE = "en-US";
    long LAST_COMPONENT_ID = 0L;
}
