package org.mumue.mumue.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class DatabaseConfigurationLoader {
    public DatabaseConfiguration load() {
        return createConfiguration(new Properties());
    }

    public DatabaseConfiguration load(String filePath) {
        return createConfiguration(loadProperties(filePath));
    }

    private DatabaseConfiguration createConfiguration(Properties properties) {
        return new DatabaseConfiguration(properties);
    }

    private Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(new InputStreamReader(inputStream));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return properties;
    }
}
