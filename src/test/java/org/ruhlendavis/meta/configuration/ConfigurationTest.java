package org.ruhlendavis.meta.configuration;

import static org.junit.Assert.assertEquals;

import com.google.common.io.Resources;
import java.net.URISyntaxException;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ConfigurationTest {
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
    public void loadLoadsConfiguration() throws URISyntaxException {
        Configuration configuration = new Configuration();
        String path = Resources.getResource("org/ruhlendavis/meta/configuration/configuration.properties").toURI().getPath();
        configuration.load(path);
        assertEquals(9999, configuration.getPort());
    }
}
