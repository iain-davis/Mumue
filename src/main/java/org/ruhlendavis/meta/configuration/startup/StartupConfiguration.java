package org.ruhlendavis.meta.configuration.startup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import org.ruhlendavis.meta.constants.Defaults;
import org.ruhlendavis.meta.constants.OptionName;

public class StartupConfiguration {
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

    public int getTelnetPort() {
        String port = properties.getProperty(OptionName.TELNET_PORT, Defaults.TELNET_PORT_OLD);
        return Integer.parseInt(port);
    }

    public void setTelnetPort(int port) {
        properties.setProperty(OptionName.TELNET_PORT, String.valueOf(port));
    }

    public String getDatabasePath() {
        return properties.getProperty(OptionName.DATABASE_PATH, Defaults.DATABASE_PATH);
    }

    public String getDatabaseUsername() {
        return properties.getProperty(OptionName.DATABASE_USERNAME, Defaults.DATABASE_USERNAME);
    }

    public String getDatabasePassword() {
        return properties.getProperty(OptionName.DATABASE_PASSWORD, Defaults.DATABASE_PASSWORD);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
