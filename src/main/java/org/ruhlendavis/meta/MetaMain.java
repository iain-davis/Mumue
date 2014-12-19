package org.ruhlendavis.meta;

import java.io.File;
import java.io.PrintStream;

import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.meta.configuration.commandline.CommandLineProvider;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationAnalyzer;
import org.ruhlendavis.meta.configuration.startup.FileFactory;
import org.ruhlendavis.meta.configuration.Defaults;
import org.ruhlendavis.meta.datastore.DataStore;
import org.ruhlendavis.meta.listener.Listener;

public class MetaMain {
    public static void main(String... arguments) {
        MetaMain metaMain = new MetaMain();
        metaMain.run(System.out, arguments);
    }

    private StartupConfiguration startupConfiguration = new StartupConfiguration();
    private StartupConfigurationAnalyzer startupConfigurationAnalyzer = new StartupConfigurationAnalyzer();
    private DataStore dataStore = new DataStore();
    private FileFactory fileFactory = new FileFactory();
    private Listener listener = new Listener();
    private Thread thread = new Thread(listener);

    public void run(PrintStream output, String... arguments) {
        String path = Defaults.CONFIGURATION_PATH;
        if (arguments.length == 1) {
            path = arguments[0];
        }
        File file = fileFactory.createFile(path);
        if (file.exists() && !file.isDirectory()) {
            startupConfiguration.load(path);
            if (!startupConfigurationAnalyzer.isValid(startupConfiguration)) {
                output.println("CRITICAL: Configuration file '" + path + "' is invalid.");
                return;
            }
        } else {
            if (arguments.length == 1) {
                output.println("CRITICAL: Configuration file '" + path + "' not found.");
                return;
            }
        }

        CommandLineConfiguration commandLineConfiguration = new CommandLineConfiguration(new CommandLineProvider(arguments).get());

        if (dataStore.isDatabaseEmpty(startupConfiguration)) {
            dataStore.populateDatabase(startupConfiguration);
        }

        listener.setPort(startupConfiguration.getTelnetPort());
        thread.start();

        while (listener.isRunning());
    }

    public int getPort() {
        return 0;
    }
}
