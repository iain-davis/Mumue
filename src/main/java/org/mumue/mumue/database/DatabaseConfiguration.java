package org.mumue.mumue.database;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class DatabaseConfiguration {
    static final String DEFAULT_DRIVER_CLASS_NAME = "org.h2.Driver";
    static final String DEFAULT_PASSWORD = "mumuedatabase";
    static final String DEFAULT_PATH = "./mumuedatabase";
    static final String DEFAULT_USERNAME = "mumuedatabase";
    static final String DATABASE_PASSWORD = "database-password";
    static final String DATABASE_PATH = "database-path";
    static final String DATABASE_USERNAME = "database-username";
    static final String DATABASE_URL = "database-url";
    static final String DATABASE_DRIVER_NAME = "database-driver-name";
    private final Properties properties;

    public DatabaseConfiguration(Properties properties) {
        this.properties = properties;
    }

    public String getPath() {
        return properties.getProperty(DATABASE_PATH, DEFAULT_PATH);
    }

    public String getUsername() {
        return properties.getProperty(DATABASE_USERNAME, DEFAULT_USERNAME);
    }

    public String getPassword() {
        return properties.getProperty(DATABASE_PASSWORD, DEFAULT_PASSWORD);
    }

    public String getUrl() {
        String url = properties.getProperty(DATABASE_URL);
        if (StringUtils.isBlank(url)) {
            return "jdbc:h2:" + getPath() + ";MV_STORE=FALSE;MVCC=FALSE;NON_KEYWORDS=value";
        }
        return url;
    }

    public String getDriverName() {
        return properties.getProperty(DATABASE_DRIVER_NAME, DEFAULT_DRIVER_CLASS_NAME);
    }
}
