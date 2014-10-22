package org.ruhlendavis.meta;

import java.io.File;
import java.io.PrintStream;
import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.ConfigurationAnalyzer;
import org.ruhlendavis.meta.configuration.FileFactory;
import org.ruhlendavis.meta.datastore.DataStore;
import org.ruhlendavis.meta.listener.Listener;

public class MetaMain {
    private Configuration configuration = new Configuration();
    private FileFactory fileFactory = new FileFactory();
    private Listener listener = new Listener();
    private Thread thread = new Thread(listener);
    private ConfigurationAnalyzer configurationAnalyzer = new ConfigurationAnalyzer();

    public void run(String[] arguments, PrintStream output) {
        String path = GlobalConstants.DEFAULT_CONFIGURATION_PATH;
        if (arguments.length == 1) {
            path = arguments[0];
        }
        File file = fileFactory.createFile(path);
        if (file.exists() && !file.isDirectory()) {
            configuration.load(path);
            if (!configurationAnalyzer.isValid(configuration)) {
                output.println("CRITICAL: Configuration file '" + path + "' is invalid.");
                return;
            }
        } else {
            if (arguments.length == 1) {
                output.println("CRITICAL: Configuration file '" + path + "' not found.");
                return;
            }
        }
        listener.setPort(configuration.getTelnetPort());
        thread.start();
        DataStore dataStore = new DataStore();
        dataStore.setupConnection(configuration);
        dataStore.setupDatabase();
        while (listener.isRunning());
    }
}
