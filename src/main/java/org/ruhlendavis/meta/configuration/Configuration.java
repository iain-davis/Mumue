package org.ruhlendavis.meta.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.ruhlendavis.meta.GlobalConstants;

public class Configuration {
    private Properties properties = new Properties();
    private FileFactory fileFactory = new FileFactory();
    private OutputStreamFactory outputStreamFactory = new OutputStreamFactory();

    public void load(String path) {
        try {
            InputStream input = FileUtils.openInputStream(fileFactory.createFile(path));
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String path) {
        try {
            OutputStream output = outputStreamFactory.createOutputStream(path);
            properties.store(output, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return Integer.parseInt(properties.getProperty(GlobalConstants.OPTION_NAME_TELNET_PORT, GlobalConstants.DEFAULT_TELNET_PORT));
    }

    public void setPort(int port) {
        properties.setProperty(GlobalConstants.OPTION_NAME_TELNET_PORT, String.valueOf(port));
    }

    public String getDatabasePath() {
        return properties.getProperty(GlobalConstants.OPTION_NAME_DATABASE_PATH, GlobalConstants.DEFAULT_DATABASE_PATH);
    }

    public String getDatabaseUsername() {
        return properties.getProperty(GlobalConstants.OPTION_NAME_DATABASE_USERNAME, GlobalConstants.DEFAULT_DATABASE_USERNAME);
    }

    public String getDatabasePassword() {
        return properties.getProperty(GlobalConstants.OPTION_NAME_DATABASE_PASSWORD, GlobalConstants.DEFAULT_DATABASE_PASSWORD);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
