package org.mumue.mumue.database;

import java.util.Properties;

public class DatabaseConfigurationBuilder {
    private final Properties properties = new Properties();

    public DatabaseConfigurationBuilder with(String key, String value) {
        properties.setProperty(key, value);
        return this;
    }

    public DatabaseConfiguration build() {
        return new DatabaseConfiguration(properties);
    }
}
