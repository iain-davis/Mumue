package org.ruhlendavis.meta.configuration.startup;

import java.io.File;

public class StartupConfigurationFactory {
    private FileFactory fileFactory = new FileFactory();

    public StartupConfiguration create(String path) {
        StartupConfiguration startupConfiguration = new StartupConfiguration();
        File file = fileFactory.create(path);
        if (file.exists() && !file.isDirectory()) {
            return startupConfiguration;
        } else {
            throw new StartupConfigurationNotFound("Startup configuration file '" + path + "' not found.");
        }
    }
}
