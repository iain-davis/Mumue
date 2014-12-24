package org.ruhlendavis.meta.configuration.startup;

import java.util.Properties;

import org.ruhlendavis.meta.configuration.ConfigurationDefaults;

public class StartupConfiguration {
    private Properties properties = new Properties();

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

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
