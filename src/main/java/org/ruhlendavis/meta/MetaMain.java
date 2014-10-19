package org.ruhlendavis.meta;

import java.io.File;
import java.io.PrintStream;
import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.FileFactory;
import org.ruhlendavis.meta.listener.Listener;

public class MetaMain {
    private Configuration configuration = new Configuration();
    private FileFactory fileFactory = new FileFactory();
    private Listener listener = new Listener();
    private Thread thread = new Thread(listener);

    public void run(String[] arguments, PrintStream output) {
        String path = GlobalConstants.DEFAULT_CONFIGURATION_PATH;
        if (arguments.length == 1) {
            path = arguments[0];
        }
        File file = fileFactory.createFile(path);
        if (file.exists() && !file.isDirectory()) {
            configuration.load(path);
        } else {
            if (arguments.length == 0) {
                output.println("WARNING: Configuration file '" + path + "' not found.");
            }
            else {
                output.println("CRITICAL: Configuration file '" + path + "' not found.");
                return;
            }
        }
        listener.setPort(configuration.getPort());
        thread.start();
        while (listener.isRunning());
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
