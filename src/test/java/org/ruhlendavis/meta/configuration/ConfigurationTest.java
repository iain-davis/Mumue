package org.ruhlendavis.meta.configuration;

import static org.junit.Assert.assertEquals;

import com.google.common.io.Resources;
import java.net.URISyntaxException;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.ruhlendavis.meta.GlobalConstants;

public class ConfigurationTest {

    @Test
    public void loadLoadsConfiguration() throws URISyntaxException {
        Configuration configuration = new Configuration();
        String path = Resources.getResource("org/ruhlendavis/meta/configuration/" + GlobalConstants.DEFAULT_CONFIGURATION_PATH).toURI().getPath();
        configuration.load(path);
        assertEquals(9999, configuration.getPort());
    }

    @Test
    public void getPortReturnsPortProperty() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        configuration.setProperties(properties);
        String port = RandomStringUtils.randomNumeric(4);
        properties.setProperty(GlobalConstants.OPTION_NAME_TELNET_PORT, port);

        assertEquals(Integer.parseInt(port), configuration.getPort());
    }

    @Test
    public void getPortReturnsDefaultPort() {
        Configuration configuration = new Configuration();
        assertEquals(Integer.parseInt(GlobalConstants.DEFAULT_TELNET_PORT), configuration.getPort());
    }

    @Test
    public void setPortSetsPort() {
        Configuration configuration = new Configuration();
        int port = RandomUtils.nextInt(1, 65536);
        configuration.setPort(port);
        assertEquals(port, configuration.getPort());
    }

    @Test
    public void getDatabasePathReturnsDatabasePath() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        configuration.setProperties(properties);
        String path = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(GlobalConstants.OPTION_NAME_DATABASE_PATH, path);

        assertEquals(path, configuration.getDatabasePath());
    }

    @Test
    public void getDatabasePathReturnsDefaultDatabasePath() {
        Configuration configuration = new Configuration();
        assertEquals(GlobalConstants.DEFAULT_DATABASE_PATH, configuration.getDatabasePath());
    }

    @Test
    public void getDatabaseUsernameReturnsDatabaseUsername() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        configuration.setProperties(properties);
        String username = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(GlobalConstants.OPTION_NAME_DATABASE_USERNAME, username);

        assertEquals(username, configuration.getDatabaseUsername());
    }

    @Test
    public void getDatabaseUsernameReturnsDefaultDatabaseUsername() {
        Configuration configuration = new Configuration();
        assertEquals(GlobalConstants.DEFAULT_DATABASE_USERNAME, configuration.getDatabaseUsername());
    }

    @Test
    public void getDatabaseUsernameReturnsDatabasePassword() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        configuration.setProperties(properties);
        String password = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty(GlobalConstants.OPTION_NAME_DATABASE_PASSWORD, password);

        assertEquals(password, configuration.getDatabasePassword());
    }

    @Test
    public void getDatabasePasswordReturnsDefaultDatabasePassword() {
        Configuration configuration = new Configuration();
        assertEquals(GlobalConstants.DEFAULT_DATABASE_PASSWORD, configuration.getDatabasePassword());
    }

}
