package org.ruhlendavis.meta.configuration.startup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import org.ruhlendavis.meta.configuration.ConfigurationDefaults;

public class StartupConfiguration {
    private Properties properties = new Properties();
    private FileFactory fileFactory = new FileFactory();
    private OutputStreamFactory outputStreamFactory = new OutputStreamFactory();

    public void load(String path) {
        try {
            InputStream input = FileUtils.openInputStream(fileFactory.create(path));
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String path) {
        try {
            OutputStream output = outputStreamFactory.create(path);
            properties.store(output, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTelnetPort() {
        String port = properties.getProperty(StartupConfigurationOptionName.TELNET_PORT, String.valueOf(ConfigurationDefaults.TELNET_PORT));
        return Integer.parseInt(port);
    }

    public void setTelnetPort(int port) {
        properties.setProperty(StartupConfigurationOptionName.TELNET_PORT, String.valueOf(port));
    }

    public String getDatabasePath() {
        return properties.getProperty(StartupConfigurationOptionName.DATABASE_PATH, ConfigurationDefaults.DATABASE_PATH);
    }

    public String getDatabaseUsername() {
        return properties.getProperty(StartupConfigurationOptionName.DATABASE_USERNAME, ConfigurationDefaults.DATABASE_USERNAME);
    }

    public String getDatabasePassword() {
        return properties.getProperty(StartupConfigurationOptionName.DATABASE_PASSWORD, ConfigurationDefaults.DATABASE_PASSWORD);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
