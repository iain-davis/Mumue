package org.ruhlendavis.meta.configuration;

import static org.junit.Assert.assertEquals;

import com.google.common.io.Resources;
import java.net.URISyntaxException;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
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
        properties.setProperty("port", port);

        assertEquals(Integer.parseInt(port), configuration.getPort());
    }

    @Test
    public void getPortReturnsDefaultPort() {
        Configuration configuration = new Configuration();
        assertEquals(Integer.parseInt(GlobalConstants.DEFAULT_PORT), configuration.getPort());
    }

    @Test
    public void getDatabasePathReturnsDatabasePath() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        configuration.setProperties(properties);
        String path = RandomStringUtils.randomAlphabetic(17);
        properties.setProperty("database-path", path);

        assertEquals(path, configuration.getDatabasePath());
    }

    @Test
    public void getDatabasePathReturnsDefaultDatabasePath() {
        Configuration configuration = new Configuration();
        assertEquals(GlobalConstants.DEFAULT_DATABASE_PATH, configuration.getDatabasePath());
    }
}
