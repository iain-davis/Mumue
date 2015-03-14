package org.ruhlendavis.mumue.configuration.startup;

import java.util.Properties;

import javax.inject.Singleton;

import org.ruhlendavis.mumue.configuration.ConfigurationDefaults;

@Singleton
public class StartupConfiguration {
    private final Properties properties;

    public StartupConfiguration(Properties properties) {
        this.properties = properties;
    }

    public int getTelnetPort() {
        String port = properties.getProperty(StartupConfigurationOptionName.TELNET_PORT, String.valueOf(ConfigurationDefaults.TELNET_PORT));
        return Integer.parseInt(port);
    }

    public String getDatabasePath() {
        return properties.getProperty(StartupConfigurationOptionName.DATABASE_PATH, ConfigurationDefaults.DATABASE_PATH);
    }

    public String getDatabaseUsername() {
        return properties.getProperty(StartupConfigurationOptionName.DATABASE_USERNAME, ConfigurationDefaults.DATABASE_USERNAME);
    }

    public String getDatabasePassword() {
        return properties.getProperty(StartupConfigurationOptionName.DATABASE_PASSWORD, ConfigurationDefaults.DATABASE_PASSWORD);
    }
}
