package org.ruhlendavis.meta;

import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

public class MetaMain {
    private String configurationPath = "configuration.properties";

    public void run(String[] arguments) {
        if (arguments.length == 1 && StringUtils.isNotBlank(arguments[0])) {
            configurationPath = arguments[0];
        }
    }

    public String getConfigurationPath() {
        return configurationPath;
    }

    public void setConfigurationPath(String configurationPath) {
        this.configurationPath = configurationPath;
    }
}
