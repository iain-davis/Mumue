package org.ruhlendavis.meta.configuration.startup;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import org.ruhlendavis.meta.configuration.ConfigurationDefaults;

public class StartupConfigurationTest {
    @Test
    public void getPortReturnsPortProperty() {
        StartupConfiguration startupConfiguration = new StartupConfiguration();
        Properties properties = new Properties();
        startupConfiguration.setProperties(properties);
        String port = RandomStringUtils.randomNumeric(4);
        properties.setProperty(StartupConfigurationOptionName.TELNET_PORT, port);

        assertEquals(Integer.parseInt(port), startupConfiguration.getTelnetPort());
    }

    @Test
    public void getPortReturnsDefaultPort() {
        StartupConfiguration startupConfiguration = new StartupConfiguration();
        assertEquals(ConfigurationDefaults.TELNET_PORT, startupConfiguration.getTelnetPort());
    }

    @Test
    public void getDatabasePathReturnsDatabasePath() {
        StartupConfiguration startupConfiguration = new StartupConfiguration();
        Properties properties = new Properties();
        startupConfiguration.setProperties(properties);
        String path = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(StartupConfigurationOptionName.DATABASE_PATH, path);

        assertEquals(path, startupConfiguration.getDatabasePath());
    }

    @Test
    public void getDatabasePathReturnsDefaultDatabasePath() {
        StartupConfiguration startupConfiguration = new StartupConfiguration();
        assertEquals(ConfigurationDefaults.DATABASE_PATH, startupConfiguration.getDatabasePath());
    }

    @Test
    public void getDatabaseUsernameReturnsDatabaseUsername() {
        StartupConfiguration startupConfiguration = new StartupConfiguration();
        Properties properties = new Properties();
        startupConfiguration.setProperties(properties);
        String username = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(StartupConfigurationOptionName.DATABASE_USERNAME, username);

        assertEquals(username, startupConfiguration.getDatabaseUsername());
    }

    @Test
    public void getDatabaseUsernameReturnsDefaultDatabaseUsername() {
        StartupConfiguration startupConfiguration = new StartupConfiguration();
        assertEquals(ConfigurationDefaults.DATABASE_USERNAME, startupConfiguration.getDatabaseUsername());
    }

    @Test
    public void getDatabaseUsernameReturnsDatabasePassword() {
        StartupConfiguration startupConfiguration = new StartupConfiguration();
        Properties properties = new Properties();
        startupConfiguration.setProperties(properties);
        String password = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(StartupConfigurationOptionName.DATABASE_PASSWORD, password);

        assertEquals(password, startupConfiguration.getDatabasePassword());
    }

    @Test
    public void getDatabasePasswordReturnsDefaultDatabasePassword() {
        StartupConfiguration startupConfiguration = new StartupConfiguration();
        assertEquals(ConfigurationDefaults.DATABASE_PASSWORD, startupConfiguration.getDatabasePassword());
    }
}
