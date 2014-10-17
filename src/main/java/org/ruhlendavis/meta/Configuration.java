package org.ruhlendavis.meta;

import java.util.Properties;

public class Configuration {
    private Properties properties = new Properties();

    public int getPort() {
        return Integer.parseInt(properties.getProperty("port"));
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
