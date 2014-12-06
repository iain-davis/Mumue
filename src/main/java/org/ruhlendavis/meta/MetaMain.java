package org.ruhlendavis.meta;

import java.io.File;
import java.io.PrintStream;

import org.apache.commons.cli.CommandLine;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.commandline.CommandLineProvider;
import org.ruhlendavis.meta.configuration.file.FileConfiguration;
import org.ruhlendavis.meta.configuration.file.FileConfigurationAnalyzer;
import org.ruhlendavis.meta.configuration.file.FileFactory;
import org.ruhlendavis.meta.datastore.DataStore;
import org.ruhlendavis.meta.listener.Listener;

public class MetaMain {
    public static void main(String... arguments) {
        MetaMain metaMain = new MetaMain();
        metaMain.run(System.out, arguments);
    }

    private FileConfiguration fileConfiguration = new FileConfiguration();
    private FileConfigurationAnalyzer fileConfigurationAnalyzer = new FileConfigurationAnalyzer();
    private DataStore dataStore = new DataStore();
    private FileFactory fileFactory = new FileFactory();
    private Listener listener = new Listener();
    private Thread thread = new Thread(listener);

    public void run(PrintStream output, String... arguments) {
        String path = GlobalConstants.DEFAULT_CONFIGURATION_PATH;
        if (arguments.length == 1) {
            path = arguments[0];
        }
        File file = fileFactory.createFile(path);
        if (file.exists() && !file.isDirectory()) {
            fileConfiguration.load(path);
            if (!fileConfigurationAnalyzer.isValid(fileConfiguration)) {
                output.println("CRITICAL: Configuration file '" + path + "' is invalid.");
                return;
            }
        } else {
            if (arguments.length == 1) {
                output.println("CRITICAL: Configuration file '" + path + "' not found.");
                return;
            }
        }

//        Configuration configuration = new Configuration(new CommandLineProvider().get(arguments), fileConfiguration);

        if (dataStore.isDatabaseEmpty(fileConfiguration)) {
            dataStore.populateDatabase(fileConfiguration);
        }

        listener.setPort(fileConfiguration.getTelnetPort());
        thread.start();

        while (listener.isRunning());
    }

    public int getPort() {
        return 0;
    }
}
