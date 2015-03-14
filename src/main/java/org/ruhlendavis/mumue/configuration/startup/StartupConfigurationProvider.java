package org.ruhlendavis.mumue.configuration.startup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.inject.Inject;

import org.ruhlendavis.mumue.configuration.ConfigurationDefaults;
import org.ruhlendavis.mumue.configuration.commandline.CommandLineConfiguration;

public class StartupConfigurationProvider {
    private final CommandLineConfiguration commandLineConfiguration;

    private Properties properties = new Properties();
    private FileFactory fileFactory = new FileFactory();
    private FileInputStreamFactory fileInputStreamFactory = new FileInputStreamFactory();
    private FileOutputStreamFactory fileOutputStreamFactory = new FileOutputStreamFactory();

    @Inject
    public StartupConfigurationProvider(CommandLineConfiguration commandLineConfiguration) {
        this.commandLineConfiguration = commandLineConfiguration;
    }

    public StartupConfigurationProvider(CommandLineConfiguration commandLineConfiguration, Properties properties, FileFactory fileFactory, FileInputStreamFactory fileInputStreamFactory, FileOutputStreamFactory fileOutputStreamFactory) {
        this.commandLineConfiguration = commandLineConfiguration;
        this.properties = properties;
        this.fileFactory = fileFactory;
        this.fileInputStreamFactory = fileInputStreamFactory;
        this.fileOutputStreamFactory = fileOutputStreamFactory;
    }

    public StartupConfiguration get() {
        String path = commandLineConfiguration.getStartupConfigurationPath();
        StartupConfiguration startupConfiguration = new StartupConfiguration(properties);
        File file = fileFactory.create(path);
        if (file.isFile()) {
            loadStartupConfiguration(file, properties);
        } else {
            setStartupConfigurationDefaults(properties);
            writeStartupConfiguration(file, properties);
        }
        return startupConfiguration;
    }

    private void loadStartupConfiguration(File file, Properties properties) {
        try {
            InputStream input = fileInputStreamFactory.create(file);
            properties.load(input);
        } catch (IOException exception) {
            throw new RuntimeException("Exception while opening configuration properties file for input", exception);
        }
    }

    private void writeStartupConfiguration(File file, Properties properties) {
        try {
            OutputStream output = fileOutputStreamFactory.create(file);
            properties.store(output, "");
        } catch (IOException exception) {
            throw new RuntimeException("Exception while opening configuration properties file for output", exception);
        }
    }

    private void setStartupConfigurationDefaults(Properties properties) {
        properties.setProperty(StartupConfigurationOptionName.DATABASE_PASSWORD, ConfigurationDefaults.DATABASE_PASSWORD);
        properties.setProperty(StartupConfigurationOptionName.DATABASE_PATH, ConfigurationDefaults.DATABASE_PATH);
        properties.setProperty(StartupConfigurationOptionName.DATABASE_USERNAME, ConfigurationDefaults.DATABASE_USERNAME);
        properties.setProperty(StartupConfigurationOptionName.TELNET_PORT, String.valueOf(ConfigurationDefaults.TELNET_PORT));
    }
}
