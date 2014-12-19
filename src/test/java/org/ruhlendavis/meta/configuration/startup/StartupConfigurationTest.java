package org.ruhlendavis.meta.configuration.startup;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import com.google.common.io.Resources;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Defaults;

@RunWith(MockitoJUnitRunner.class)
public class StartupConfigurationTest {
    @Mock private OutputStreamFactory outputStreamFactory;
    @InjectMocks private StartupConfiguration startupConfiguration = new StartupConfiguration();

    @Test
    public void loadLoadsConfiguration() throws URISyntaxException {
        StartupConfiguration startupConfiguration = new StartupConfiguration();
        String path = Resources.getResource("org/ruhlendavis/meta/configuration/startup/" + Defaults.CONFIGURATION_PATH).toURI().getPath();
        startupConfiguration.load(path);
        assertEquals(9998, startupConfiguration.getTelnetPort());
    }

    @Test
    public void saveSavesConfiguration() throws URISyntaxException, IOException {
        OutputStream output = mock(OutputStream.class);
        Properties properties = mock(Properties.class);
        startupConfiguration.setProperties(properties);
        doNothing().when(properties).store(any(OutputStream.class), anyString());
        when(outputStreamFactory.createOutputStream(anyString())).thenReturn(output);
        String path = RandomStringUtils.randomAlphabetic(13);
        startupConfiguration.save(path);
        verify(outputStreamFactory).createOutputStream(eq(path));
        verify(properties).store(any(OutputStream.class), anyString());
    }

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
        assertEquals(Integer.parseInt(Defaults.TELNET_PORT_OLD), startupConfiguration.getTelnetPort());
    }

    @Test
    public void setPortSetsPort() {
        StartupConfiguration startupConfiguration = new StartupConfiguration();
        int port = RandomUtils.nextInt(1, 65536);
        startupConfiguration.setTelnetPort(port);
        assertEquals(port, startupConfiguration.getTelnetPort());
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
        assertEquals(Defaults.DATABASE_PATH, startupConfiguration.getDatabasePath());
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
        assertEquals(Defaults.DATABASE_USERNAME, startupConfiguration.getDatabaseUsername());
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
        assertEquals(Defaults.DATABASE_PASSWORD, startupConfiguration.getDatabasePassword());
    }
}
