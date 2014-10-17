package org.ruhlendavis.meta;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.io.FileUtils;

public class Configuration {
    private Properties properties = new Properties();

    public void load(String configurationPath) {
        try {
            InputStream input = FileUtils.openInputStream(FileUtils.getFile(configurationPath));
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return Integer.parseInt(properties.getProperty("port"));
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
