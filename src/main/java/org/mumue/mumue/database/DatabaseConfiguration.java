package org.mumue.mumue.database;

import org.apache.commons.lang3.StringUtils;
import org.mumue.mumue.configuration.ConfigurationDefaults;

import javax.inject.Singleton;
import java.util.Properties;

@Singleton
class DatabaseConfiguration {
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
        return properties.getProperty(DATABASE_PATH, ConfigurationDefaults.DATABASE_PATH);
    }

    public String getUsername() {
        return properties.getProperty(DATABASE_USERNAME, ConfigurationDefaults.DATABASE_USERNAME);
    }

    public String getPassword() {
        return properties.getProperty(DATABASE_PASSWORD, ConfigurationDefaults.DATABASE_PASSWORD);
    }

    public String getUrl() {
        String url = properties.getProperty(DATABASE_URL);
        if (StringUtils.isBlank(url)) {
            return "jdbc:h2:" + getPath() + ";MV_STORE=FALSE;MVCC=FALSE";
        }
        return url;
    }

    public String getDriverName() {
        return properties.getProperty(DATABASE_DRIVER_NAME, SqlConstants.DRIVER_CLASS_NAME);
    }
}
