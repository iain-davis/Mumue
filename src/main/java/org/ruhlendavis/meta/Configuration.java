package org.ruhlendavis.meta;

import java.util.Properties;

public class Configuration {
    private Properties properties = new Properties();

    public String getPort() {
        return properties.getProperty("port");
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
