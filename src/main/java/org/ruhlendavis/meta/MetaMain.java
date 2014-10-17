package org.ruhlendavis.meta;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.ruhlendavis.meta.listener.Listener;

public class MetaMain {
    private String configurationPath = "configuration.properties";
    private Configuration configuration = new Configuration();
    private ConfigurationPrompter prompter = new ConfigurationPrompter();
    private Properties properties = new Properties();
    private Listener listener = new Listener();

    public void run(String[] arguments) {
        if(loadProperties(arguments)) {
            listener.setPort(configuration.getPort());
            new Thread(listener).start();
            while (listener.isRunning()) ;
        }
    }

    private boolean loadProperties(String[] arguments) {
        if (arguments.length == 1 && StringUtils.isNotBlank(arguments[0])) {
            configurationPath = arguments[0];
        }
        InputStream input;
        try {
            input = FileUtils.openInputStream(FileUtils.getFile(configurationPath));
            properties.load(input);
            configuration.setProperties(properties);
        } catch (FileNotFoundException exception) {
            if (arguments.length == 0) {
                prompter.run(System.in, System.out, properties);
                return true;
            }
            System.out.println("Configuration file '" + configurationPath + "' not found.");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getConfigurationPath() {
        return configurationPath;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
