package org.ruhlendavis.meta.configuration;

public interface ConfigurationDefaults {
    // Command line section
    String CONFIGURATION_PATH = "startup-configuration.properties";

    // Startup configuration section
    String DATABASE_PASSWORD = "metadatabase";
    String DATABASE_PATH = "./metadatabase";
    String DATABASE_USERNAME = "metadatabase";
    int TELNET_PORT = 9999;

    // Primary configuration section
    String SERVER_LOCALE = "en-US";
}
