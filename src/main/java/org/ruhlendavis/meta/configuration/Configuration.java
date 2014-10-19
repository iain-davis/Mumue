package org.ruhlendavis.meta.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.ruhlendavis.meta.GlobalConstants;

public class Configuration {
    private Properties properties = new Properties();

    public void load(String configurationPath) {
        try {
            InputStream input = FileUtils.openInputStream(new FileFactory().createFile(configurationPath));
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return Integer.parseInt(properties.getProperty("port", GlobalConstants.DEFAULT_PORT));
    }

    public String getDatabasePath() {
        return properties.getProperty("database-path", GlobalConstants.DEFAULT_DATABASE_PATH);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
