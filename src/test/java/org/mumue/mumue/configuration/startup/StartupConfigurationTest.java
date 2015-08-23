package org.mumue.mumue.configuration.startup;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mumue.mumue.configuration.ConfigurationDefaults;

import java.util.Properties;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class StartupConfigurationTest {
    @Test
    public void getPortReturnsPortProperty() {
        Properties properties = new Properties();
        StartupConfiguration startupConfiguration = new StartupConfiguration(properties);
        String port = RandomStringUtils.randomNumeric(4);
        properties.setProperty(StartupConfigurationOptionName.TELNET_PORT, port);

        assertEquals(Integer.parseInt(port), startupConfiguration.getTelnetPort());
    }

    @Test
    public void getPortReturnsDefaultPort() {
        StartupConfiguration startupConfiguration = new StartupConfiguration(new Properties());
        Assert.assertEquals(ConfigurationDefaults.TELNET_PORT, startupConfiguration.getTelnetPort());
    }

    @Test
    public void getDatabasePathReturnsDatabasePath() {
        Properties properties = new Properties();
        StartupConfiguration startupConfiguration = new StartupConfiguration(properties);

        String path = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(StartupConfigurationOptionName.DATABASE_PATH, path);

        assertEquals(path, startupConfiguration.getDatabasePath());
    }

    @Test
    public void getDatabasePathReturnsDefaultDatabasePath() {
        StartupConfiguration startupConfiguration = new StartupConfiguration(new Properties());
        assertEquals(ConfigurationDefaults.DATABASE_PATH, startupConfiguration.getDatabasePath());
    }

    @Test
    public void getDatabaseUsernameReturnsDatabaseUsername() {
        Properties properties = new Properties();
        StartupConfiguration startupConfiguration = new StartupConfiguration(properties);

        String username = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(StartupConfigurationOptionName.DATABASE_USERNAME, username);

        assertEquals(username, startupConfiguration.getDatabaseUsername());
    }

    @Test
    public void getDatabaseUsernameReturnsDefaultDatabaseUsername() {
        StartupConfiguration startupConfiguration = new StartupConfiguration(new Properties());
        assertEquals(ConfigurationDefaults.DATABASE_USERNAME, startupConfiguration.getDatabaseUsername());
    }

    @Test
    public void getDatabaseUsernameReturnsDatabasePassword() {
        Properties properties = new Properties();
        StartupConfiguration startupConfiguration = new StartupConfiguration(properties);

        String password = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(StartupConfigurationOptionName.DATABASE_PASSWORD, password);

        assertEquals(password, startupConfiguration.getDatabasePassword());
    }

    @Test
    public void getDatabasePasswordReturnsDefaultDatabasePassword() {
        StartupConfiguration startupConfiguration = new StartupConfiguration(new Properties());
        assertEquals(ConfigurationDefaults.DATABASE_PASSWORD, startupConfiguration.getDatabasePassword());
    }

    @Test
    public void getDatabaseUsernameReturnsDatabaseUrl() {
        Properties properties = new Properties();
        StartupConfiguration startupConfiguration = new StartupConfiguration(properties);

        String url = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(StartupConfigurationOptionName.DATABASE_URL, url);

        assertThat(startupConfiguration.getDatabaseUrl(), equalTo(url));
    }

    @Test
    public void getDatabasePasswordReturnsDefaultDatabaseUrl() {
        StartupConfiguration startupConfiguration = new StartupConfiguration(new Properties());
        assertThat(startupConfiguration.getDatabaseUrl(), equalTo(ConfigurationDefaults.DATABASE_URL));
    }
}
