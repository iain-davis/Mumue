package org.ruhlendavis.meta;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class MetaMain {
    private String configurationPath = "configuration.properties";
    private Configuration configuration = new Configuration();
    private ConfigurationPrompter prompter = new ConfigurationPrompter();

    public void run(String[] arguments) {
        if (arguments.length == 1 && StringUtils.isNotBlank(arguments[0])) {
            configurationPath = arguments[0];
        }
        Properties properties = new Properties();
        InputStream input;
        try {
            input = FileUtils.openInputStream(FileUtils.getFile(configurationPath));
            properties.load(input);
            configuration.setProperties(properties);
        } catch (FileNotFoundException exception) {
            if (arguments.length == 0) {
                prompter.run(System.in, System.out, properties);
            }
            System.out.println("Configuration file '" + configurationPath + "' not found.");
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getConfigurationPath() {
        return configurationPath;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
