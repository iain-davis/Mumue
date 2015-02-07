package org.ruhlendavis.meta.configuration.startup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.ruhlendavis.meta.configuration.ConfigurationDefaults;

public class StartupConfigurationFactory {
    private Properties properties = new Properties();
    private FileFactory fileFactory = new FileFactory();
    private FileInputStreamFactory fileInputStreamFactory = new FileInputStreamFactory();
    private OutputStreamFactory outputStreamFactory = new OutputStreamFactory();

    public StartupConfiguration create(String path) {
        StartupConfiguration startupConfiguration = new StartupConfiguration();
        startupConfiguration.setProperties(properties);
        File file = fileFactory.create(path);
        if (file.isFile()) {
            loadStartupConfiguration(path, properties);
        } else {
            setStartupConfigurationDefaults(properties);
            writeStartupConfiguration(path, properties);
        }
        return startupConfiguration;
    }

    private void loadStartupConfiguration(String path, Properties properties) {
        try {
            InputStream input = fileInputStreamFactory.create(path);
            properties.load(input);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void writeStartupConfiguration(String path, Properties properties) {
        try {
            OutputStream output = outputStreamFactory.create(path);
            properties.store(output, "");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setStartupConfigurationDefaults(Properties properties) {
        properties.setProperty(StartupConfigurationOptionName.DATABASE_PASSWORD, ConfigurationDefaults.DATABASE_PASSWORD);
        properties.setProperty(StartupConfigurationOptionName.DATABASE_PATH, ConfigurationDefaults.DATABASE_PATH);
        properties.setProperty(StartupConfigurationOptionName.DATABASE_USERNAME, ConfigurationDefaults.DATABASE_USERNAME);
        properties.setProperty(StartupConfigurationOptionName.TELNET_PORT, String.valueOf(ConfigurationDefaults.TELNET_PORT));
    }
}
