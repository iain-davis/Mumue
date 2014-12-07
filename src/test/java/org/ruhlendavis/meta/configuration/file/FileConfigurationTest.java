package org.ruhlendavis.meta.configuration.file;

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

import org.ruhlendavis.meta.GlobalConstants;
import org.ruhlendavis.meta.constants.Defaults;

@RunWith(MockitoJUnitRunner.class)
public class FileConfigurationTest {
    @Mock private OutputStreamFactory outputStreamFactory;
    @InjectMocks private FileConfiguration fileConfiguration = new FileConfiguration();

    @Test
    public void loadLoadsConfiguration() throws URISyntaxException {
        FileConfiguration fileConfiguration = new FileConfiguration();
        String path = Resources.getResource("org/ruhlendavis/meta/configuration/file/" + GlobalConstants.DEFAULT_CONFIGURATION_PATH).toURI().getPath();
        fileConfiguration.load(path);
        assertEquals(9998, fileConfiguration.getTelnetPort());
    }

    @Test
    public void saveSavesConfiguration() throws URISyntaxException, IOException {
        OutputStream output = mock(OutputStream.class);
        Properties properties = mock(Properties.class);
        fileConfiguration.setProperties(properties);
        doNothing().when(properties).store(any(OutputStream.class), anyString());
        when(outputStreamFactory.createOutputStream(anyString())).thenReturn(output);
        String path = RandomStringUtils.randomAlphabetic(13);
        fileConfiguration.save(path);
        verify(outputStreamFactory).createOutputStream(eq(path));
        verify(properties).store(any(OutputStream.class), anyString());
    }

    @Test
    public void getPortReturnsPortProperty() {
        FileConfiguration fileConfiguration = new FileConfiguration();
        Properties properties = new Properties();
        fileConfiguration.setProperties(properties);
        String port = RandomStringUtils.randomNumeric(4);
        properties.setProperty(GlobalConstants.OPTION_NAME_TELNET_PORT, port);

        assertEquals(Integer.parseInt(port), fileConfiguration.getTelnetPort());
    }

    @Test
    public void getPortReturnsDefaultPort() {
        FileConfiguration fileConfiguration = new FileConfiguration();
        assertEquals(Integer.parseInt(GlobalConstants.DEFAULT_TELNET_PORT_OLD), fileConfiguration.getTelnetPort());
    }

    @Test
    public void setPortSetsPort() {
        FileConfiguration fileConfiguration = new FileConfiguration();
        int port = RandomUtils.nextInt(1, 65536);
        fileConfiguration.setTelnetPort(port);
        assertEquals(port, fileConfiguration.getTelnetPort());
    }

    @Test
    public void getDatabasePathReturnsDatabasePath() {
        FileConfiguration fileConfiguration = new FileConfiguration();
        Properties properties = new Properties();
        fileConfiguration.setProperties(properties);
        String path = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(GlobalConstants.OPTION_NAME_DATABASE_PATH, path);

        assertEquals(path, fileConfiguration.getDatabasePath());
    }

    @Test
    public void getDatabasePathReturnsDefaultDatabasePath() {
        FileConfiguration fileConfiguration = new FileConfiguration();
        assertEquals(Defaults.DATABASE_PATH, fileConfiguration.getDatabasePath());
    }

    @Test
    public void getDatabaseUsernameReturnsDatabaseUsername() {
        FileConfiguration fileConfiguration = new FileConfiguration();
        Properties properties = new Properties();
        fileConfiguration.setProperties(properties);
        String username = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(GlobalConstants.OPTION_NAME_DATABASE_USERNAME, username);

        assertEquals(username, fileConfiguration.getDatabaseUsername());
    }

    @Test
    public void getDatabaseUsernameReturnsDefaultDatabaseUsername() {
        FileConfiguration fileConfiguration = new FileConfiguration();
        assertEquals(Defaults.DATABASE_USERNAME, fileConfiguration.getDatabaseUsername());
    }

    @Test
    public void getDatabaseUsernameReturnsDatabasePassword() {
        FileConfiguration fileConfiguration = new FileConfiguration();
        Properties properties = new Properties();
        fileConfiguration.setProperties(properties);
        String password = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(GlobalConstants.OPTION_NAME_DATABASE_PASSWORD, password);

        assertEquals(password, fileConfiguration.getDatabasePassword());
    }

    @Test
    public void getDatabasePasswordReturnsDefaultDatabasePassword() {
        FileConfiguration fileConfiguration = new FileConfiguration();
        assertEquals(Defaults.DATABASE_PASSWORD, fileConfiguration.getDatabasePassword());
    }
}
