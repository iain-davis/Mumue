package org.ruhlendavis.meta;

import java.io.File;
import java.io.PrintStream;
import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.FileFactory;
import org.ruhlendavis.meta.listener.Listener;

public class MetaMain {
    private static final String DEFAULT_CONFIGURATION_PATH = "configuration.properties";
    private Configuration configuration = new Configuration();
    private FileFactory fileFactory = new FileFactory();
    private Listener listener = new Listener();

    public void run(String[] arguments, PrintStream output) {
        String path = DEFAULT_CONFIGURATION_PATH;
        if (arguments.length == 1) {
            path = arguments[0];
        }
        File file = fileFactory.createFile(path);
        if (file.exists() && !file.isDirectory()) {
            configuration.load(path);
        } else {
            output.println("Configuration file '" + path + "' not found.");
            return;
        }
        listener.setPort(configuration.getPort());
        new Thread(listener).start();
        while (listener.isRunning());
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
