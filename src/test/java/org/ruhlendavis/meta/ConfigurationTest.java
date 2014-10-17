package org.ruhlendavis.meta;

import static org.junit.Assert.assertEquals;

import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

public class ConfigurationTest {
    private Properties properties = new Properties();
    private Configuration configuration = new Configuration();

    @Before
    public void beforeEach() {
        configuration.setProperties(properties);
    }

    @Test
    public void getPortReturnsPortProperty() {
        String port = RandomStringUtils.randomNumeric(4);
        properties.setProperty("port", port);
        assertEquals(Integer.parseInt(port), configuration.getPort());
    }
}
